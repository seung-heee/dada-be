package com.dada.domain.room.dto

data class DashboardResponse(
    val meetingName: String,
    val totalMembers: List<String>,
    val votedMembers: List<String>,
    val topSchedules: List<TopScheduleDto>,
)

data class TopScheduleDto(
    val date: String,
    val availableMembers: List<String>
)