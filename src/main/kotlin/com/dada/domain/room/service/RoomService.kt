package com.dada.domain.room.service

import com.dada.domain.room.dto.RoomRequest
import com.dada.domain.room.dto.RoomResponse
import com.dada.domain.room.entity.Room
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
            availableDates = request.availableDates
        )

        // 3. DB 저장
        roomRepository.save(room)

        // 4. 생성된 roomId 반환
        return generatedRoomId
    }

    @Transactional(readOnly = true)
    fun getRoom(roomId: String): RoomResponse {
        val room = roomRepository.findByRoomId(roomId)
            ?: throw NoSuchElementException("해당하는 방을 찾을 수 없습니다: $roomId")

        return RoomResponse.from(room)
    }


    private fun generateNanoId(): String {
        val alphabet = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"
        return (1..8).map { alphabet.random() }.joinToString("")
    }
}