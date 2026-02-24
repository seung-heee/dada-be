package com.dada

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.boot.CommandLineRunner
import org.springframework.core.env.Environment

@SpringBootApplication
class BeDadaApplication {
	@Bean
	fun init(env: Environment) = CommandLineRunner {
		// 서버 시작 시 스프링이 인식한 주소를 로그에 강제로 찍습니다.
		println("=================================================")
		println("현재 스프링이 인식한 MongoDB 주소: ${env.getProperty("spring.data.mongodb.uri")}")
		println("=================================================")
	}
}

fun main(args: Array<String>) {
	runApplication<BeDadaApplication>(*args)
}
