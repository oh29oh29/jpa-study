# Hello JPA!

### 주요 내용
- H2 데이터베이스 설치 및 실행  
https://www.h2database.com/html/main.html
- persistence.xml 작성
- 간단한 엔티티 저장

### 정리
- 엔티티 매니저 팩토리는 애플리케이션 전체에서 하나만 생성 및 공유해야 한다.
- 엔티티 매니저는 쓰레드간에 공유하면 안된다. 
- JPA 의 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.