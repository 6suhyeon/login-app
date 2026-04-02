# Login App

Spring Boot 기반 로그인/로그아웃 웹 애플리케이션

## 기술 스택

- **Backend**: Spring Boot 3.2.5, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf + Bootstrap 5.3.3
- **Database**: H2 (In-Memory)
- **Build**: Gradle 8.7
- **Java**: 17

## 실행 방법

### 로컬 실행
```bash
./gradlew bootRun
```

### Docker 실행
```bash
docker compose up --build
```

실행 후 http://localhost:8080 접속

## 테스트 계정

| 아이디 | 비밀번호 | 권한 |
|--------|----------|------|
| admin  | admin1234 | ADMIN |
| user   | user1234  | USER  |

## 주요 기능

1. **로그인** - Spring Security 기반 폼 로그인 (세션 인증)
2. **로그아웃** - 세션 무효화 및 쿠키 삭제
3. **회원가입** - Bean Validation + 비밀번호 BCrypt 암호화
4. **회원 목록 조회** - JPA를 통한 RDBMS 연동
5. **세션 정보 표시** - 세션 ID, 생성 시간, 만료 시간 확인

## 프로젝트 구조

```
src/main/java/com/example/login/
├── config/          # Security 설정, 초기 데이터
├── controller/      # 로그인, 회원가입, 홈 컨트롤러
├── dto/             # 요청 DTO
├── entity/          # JPA 엔티티
├── repository/      # Spring Data JPA Repository
├── service/         # 비즈니스 로직, UserDetailsService
└── LoginApplication.java
```

## ERD

[ERD 문서 보기](docs/ERD.md)

## H2 콘솔

http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:logindb`
- Username: `sa`
- Password: (빈칸)
