package com.dada.domain.room.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class RoomRequest(
    @get:Schema(description = "방 이름", example = "OOO 정기모임")
    @field:NotBlank(message = "모임 이름은 필수입니다.")
    val name: String,

    @get:Schema(description = "초대할 멤버 이름 리스트", example = "['승희', '민수']")
    val invitedMembers: List<String> = emptyList(),

    @get:Schema(description = "투표 가능한 후보 날짜 리스트 (ISO-8601 형식)", example = "['2026-02-06', '2026-02-07']")
    @field:NotEmpty(message = "후보 날짜는 최소 하나 이상이어야 합니다.")
    val candidateDates: List<String>
)
