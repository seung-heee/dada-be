# 1. 빌드단계(build)

# gradle 이미지를 사용하여 실행 가능한 jar 파일을 만듦
FROM gradle:8-jdk17 AS builder

#  작업 디렉토리 설정
WORKDIR /home/gradle/project

# gradle 캐싱을 위해 소스 코드 복사 전 설정 파일만 먼저 복사
COPY build.gradle.kts settings.gradle.kts ./

# 의존성 다운로드(소스 코드 없이 라이브러리만 먼저 받아 캐싱)
RUN gradle dependencies --no-daemon || true

# 나머지 소스 코드 복사
COPY . .

# 빌드 실행(테스트는 제외하고 빠르게 빌드)
# 결과불은 build/libs/ 안에 .jar 파일로 생성
RUN gradle clean build -x test --no-daemon

# 2. 실행단계(run)

# 가벼운 JRE(또는 JDK) 이미지만 사용하여 JAR를 실행
FROM eclipse-temurin:17-jre

# 작업 디렉토리 설정
WORKDIR /app

# 빌드단계에서 생성된 JAR 파일을 'app.jar'라는 이름으로 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 컨테이너 실행 시 사용할 포트(스프링부트 기본값 8080)
EXPOSE 8080

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]