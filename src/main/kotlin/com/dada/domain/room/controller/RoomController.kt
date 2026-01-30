package com.dada.domain.room.controller

import com.dada.domain.room.dto.DashboardResponse
import com.dada.domain.room.dto.RoomRequest
import com.dada.domain.room.dto.RoomResponse
import com.dada.domain.room.dto.VoteRequest
import com.dada.domain.room.service.RoomService
import com.dada.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Room", description = "방 생성 및 조회 관련 API")
@RestController
@RequestMapping("/api/rooms")
class RoomController(private val roomService: RoomService) {
    /**
     * POST /api/rooms
     * 방 생성 API
     */
    @Operation(summary = "새로운 방 생성", description = "방 이름, 후보 날짜, 초대 멤버를 입력받아 새로운 방을 생성하고 roomId(NanoID)를 반환합니다.")
    @PostMapping
    fun createRoom(
        @RequestBody @Valid request: RoomRequest
    ): ResponseEntity<BaseResponse<Map<String, String>> >{
//        서비스에서 비즈니스 로직 실행 후 roomId 받음
        val roomId = roomService.createRoom(request)
        val response = mapOf("roomId" to roomId)

//        결과(roomId)를 응답에 담아서 반환
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                BaseResponse.ok(response)
            )

    }

    /**
     * GET /api/rooms/{roomId}
     * 방 상세 조회 API
     */
    @Operation(summary = "방 상세 정보 조회", description = "roomId를 통해 특정 방의 상세 정보를 조회합니다.")
    @GetMapping("/{roomId}")
    fun getRoom(
        @PathVariable() roomId: String,
    ): ResponseEntity<BaseResponse<RoomResponse>> {
        val response = roomService.getRoom(roomId)

        return ResponseEntity.ok(
            BaseResponse.ok(response)
        )
    }

    /**
     * POST /api/rooms/{roomId}/votes
     * 참여 가능한 날짜 투표 API
     */
    @Operation(summary = "참여자 투표", description = "참여자가 참여 가능한 날짜에 투표합니다.")
    @PostMapping("/{roomId}/votes")
    fun voteForRoom(
        @PathVariable roomId: String,
        @RequestBody request: VoteRequest
    ): ResponseEntity<BaseResponse<Unit>> {
        roomService.addOrUpdateVote(roomId, request)
        return ResponseEntity.ok(BaseResponse.ok()
        )
    }

    @Operation(
        summary = "대시보드 데이터 조회",
        description = "특정 방의 투표 현황, TOP 3 날짜, 참여자 및 미참여자 정보를 조회합니다."
    )
    @GetMapping("/{roomId}/dashboard")
    fun getDashboard(
        @PathVariable roomId: String,
    ): ResponseEntity<BaseResponse<DashboardResponse>> {
        val result = roomService.getRoomDashboard(roomId)
        return ResponseEntity.ok(BaseResponse.ok(result))
    }
}