package com.dada.domain.room.dto

import com.dada.domain.room.entity.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class RoomResponse(
    val roomId: String,
    val name: String,
    val invitedMembers: List<String>,
    val candidateDates: List<String>,
    val createAt: LocalDateTime,
) {
    companion object {
//         엔티티를 응답 DTO로 변환해주는 메서드
        fun from(room: Room) = RoomResponse(
            roomId = room.roomId,
            name = room.name,
            invitedMembers = room.invitedMembers,
            candidateDates = room.candidateDates,
            createAt = room.createdAt
        )
            }
}
