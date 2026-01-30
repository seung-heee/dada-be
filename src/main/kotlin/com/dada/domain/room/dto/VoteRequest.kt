package com.dada.domain.room.dto

import com.dada.domain.room.entity.Vote
import io.swagger.v3.oas.annotations.media.Schema

data class VoteRequest(
    @get:Schema(description = "참여자 이름", example = "김철수")
    val memberName: String,

    @get:Schema(description = "참여 가능한 날짜 리스트", example = "[2026-01-22, 2026-01-23]")
    val selectedDates: List<String>
) {
    fun toEntity() = Vote(
        memberName = this.memberName,
        selectedDates = this.selectedDates,
    )
}