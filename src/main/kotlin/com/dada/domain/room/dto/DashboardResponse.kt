package com.dada.domain.room.dto

data class DashboardResponse(
    val meetingName: String,
    val totalMembers: List<String>,
    val votedMembers: List<String>,
    val topSchedules: List<TopScheduleDto>,
    val hasInvitedMembers: Boolean
)

data class TopScheduleDto(
    val date: String,
    val availableMembers: List<String>,
    val rank: Int = 0 // 기본값 0으로 설정
)