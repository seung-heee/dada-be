package com.dada.domain.room.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "rooms") // MongoDB의 rooms 컬렉션에 저장됨
data class Room(
    @Id
    val id: String? = null, // MongoDB의 기본 objectId

    @Indexed(unique = true)
    val roomId: String,

    val name: String,
    val invitedMembers: List<String> = emptyList(),
    val candidateDates: List<String> = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
