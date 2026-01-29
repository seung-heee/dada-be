package com.dada.domain.room.dto

import com.dada.domain.room.entity.Vote

data class VoteRequest(
    val memberName: String,
    val selectedDated: List<String>
) {
    fun toEntity() = Vote(
        memberName = this.memberName,
        selectedDated = this.selectedDated,
    )
}