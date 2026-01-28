package com.dada.domain.room.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class RoomRequest(
    @field:NotBlank(message = "모임 이름은 필수입니다.")
    val name: String,

    val invitedMembers: List<String> = emptyList(),

    @field:NotEmpty(message = "후보 날짜는 최소 하나 이상이어야 합니다.")
    val availableDates: List<String>
)
