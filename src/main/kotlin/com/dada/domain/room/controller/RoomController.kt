package com.dada.domain.room.controller

import com.dada.domain.room.dto.RoomRequest
import com.dada.domain.room.dto.RoomResponse
import com.dada.domain.room.entity.Room
import com.dada.domain.room.service.RoomService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/rooms")
class RoomController(private val roomService: RoomService) {
    /**
     * POST /api/rooms
     * 방 생성 API
     */
    @PostMapping
    fun createRoom(
        @RequestBody @Valid request: RoomRequest
    ): ResponseEntity<Map<String, String>> {
//        서비스에서 비즈니스 로직 실행 후 roomId 받음
        val roomId = roomService.createRoom(request)

//        결과(roomId)를 응답에 담아서 반환
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapOf("roomId" to roomId))
    }

    /**
     * GET /api/rooms/{roomId}
     * 방 상세 조회 API
     */
    @GetMapping("/{roomId}")
    fun getRoom(
        @PathVariable() roomId: String,
    ): ResponseEntity<RoomResponse> {
        val response = roomService.getRoom(roomId)

        return ResponseEntity.ok(response)
    }

}