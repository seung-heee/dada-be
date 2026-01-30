package com.dada.domain.room.service

import com.dada.domain.room.dto.DashboardResponse
import com.dada.domain.room.dto.RoomRequest
import com.dada.domain.room.dto.RoomResponse
import com.dada.domain.room.dto.TopScheduleDto
import com.dada.domain.room.dto.VoteRequest
import com.dada.domain.room.entity.Room
import com.dada.domain.room.entity.Vote
import com.dada.domain.room.repository.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService(private val roomRepository: RoomRepository) {
    /**
     * 방을 생성하고 생성된 roomId를 반환한다.
     */
    @Transactional
    fun createRoom(request: RoomRequest): String {
        // 1. roomId 생성
        val generatedRoomId = generateNanoId()

        // 2. DTO를 엔티티로 변환
        val room = Room(
            roomId = generatedRoomId,
            name = request.name,
            invitedMembers = request.invitedMembers,
            candidateDates = request.candidateDates
        )

        // 3. DB 저장
        roomRepository.save(room)

        // 4. 생성된 roomId 반환
        return generatedRoomId
    }

    /**
     * roomId로 방 상세 정보를 조회한다.
     *
     */
    @Transactional(readOnly = true)
    fun getRoom(roomId: String): RoomResponse {
        val room = roomRepository.findByRoomId(roomId)
            ?: throw NoSuchElementException("해당하는 방을 찾을 수 없습니다: $roomId")

        return RoomResponse.from(room)
    }

    /**
     * 참여자가 가능한 날짜를 투표한다.
     * 참여자가 투표한 결과가 있으면 갈아끼우고, 없으면 새로 추가한다.
     */
    fun addOrUpdateVote(roomId: String, request: VoteRequest) {
        val room = roomRepository.findByRoomId(roomId)
            ?: throw NoSuchElementException("해당하는 방을 찾을 수 없습니다: $roomId")

        // 기존 투표가 있다면 먼저 제거
        room.votes.removeIf { it.memberName == request.memberName }

        // 새로운 투표 데이터 생성 및 리스트에 추가
        val newVote = request.toEntity()
        room.votes.add(newVote)

        // 변경된 방 정보를 저장
        roomRepository.save(room)
    }

    /**
     * dashboard
     */
    fun getRoomDashboard(roomId: String): DashboardResponse {
        val room = roomRepository.findByRoomId(roomId)
            ?: throw NoSuchElementException("방을 찾을 수 없습니다.")

        // 투표를 완료한 사람 리스트 추출
        val votedMembers = room.votes.map { it.memberName }

        // 날짜별 참여 가능 인원 계산 및 TOP 3 정렬
        val topSchedules = room.candidateDates.map { date ->
            val available = room.votes
                .filter { it.selectedDates.contains(date) }
                .map { it.memberName }

            TopScheduleDto(date = date, availableMembers = available)
        }
            .filter { it.availableMembers.isNotEmpty() }
            .sortedByDescending { it.availableMembers.size }
            .take(3)

        return DashboardResponse(
            meetingName = room.name,
            totalMembers = room.invitedMembers,
            votedMembers = votedMembers,
            topSchedules = topSchedules
        )
    }

    private fun generateNanoId(): String {
        val alphabet = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"
        return (1..8).map { alphabet.random() }.joinToString("")
    }
}