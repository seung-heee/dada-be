package com.dada.domain.room.repository

import com.dada.domain.room.entity.Room
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository: MongoRepository<Room, String> {
    // roomID로 방을 단건 조회하는 기능
    fun findByRoomId(roomId: String): Room?
}