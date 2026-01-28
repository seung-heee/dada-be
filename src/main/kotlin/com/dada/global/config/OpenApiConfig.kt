package com.dada.global.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun dadaOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("DADA API 명세서")
                    .description("DADA의 백엔드 API입니다. 🚀")
                    .version("v1.0.0")
            )
    }
}