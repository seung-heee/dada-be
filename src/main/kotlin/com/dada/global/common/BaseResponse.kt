package com.dada.global.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 응답 객체")
data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)
