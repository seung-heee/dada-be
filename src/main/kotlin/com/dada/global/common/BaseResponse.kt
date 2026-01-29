package com.dada.global.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 응답 객체")
data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        // 성공 응답을 위한 공통 메서드
        fun <T> ok(data: T? = null, message: String = "성공"): BaseResponse<T> {
            return BaseResponse(
                status = "SUCCESS",
                message = message,
                data = data
            )
        }

        // 실패 응답을 위한 공통 메서드
        fun <T> error(message: String, status: String = "ERROR"): BaseResponse<T> {
            return BaseResponse(
                status = status,
                message = message,
                data = null
            )
        }
    }
}
