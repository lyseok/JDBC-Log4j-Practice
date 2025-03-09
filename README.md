# JDBC & Log4j Practice Project

## 개요

이 프로젝트는 **JDBC**와 **Log4j**를 활용하여 **데이터베이스 연동 및 로깅 기능을 연습**하는 콘솔 기반 애플리케이션입니다. Oracle DB와 연결하여 SQL 쿼리를 실행하며, Log4j를 사용해 디버깅과 오류 로깅을 관리합니다.

## 사용 기술

- **Java (JDK 8 이상)**: 프로젝트의 기본 언어
- **JDBC**: 데이터베이스 연결 및 SQL 실행
- **Oracle Database**: 데이터 저장 및 조회
- **PreparedStatement & Statement**: SQL 실행 방식 연습
- **Log4j**: 로그 기록 및 디버깅
- **Properties 파일 관리**: `db.properties`, `log4j.properties` 활용
- **싱글톤 패턴**: DB 연결 관리(`DBUtil.java`)

## 프로젝트 구조

```
src/
├── basic/jdbcStudy/
│   ├── jdbcStudy01.java  // 기본 JDBC 연결 및 SELECT 실행
│   ├── jdbcStudy02.java  // 사용자 입력값을 활용한 SQL 실행
│   ├── jdbcStudy03.java  // PreparedStatement 활용
│   ├── jdbcStudy04.java  // 데이터 삽입 처리
│   ├── jdbcStudy05.java  // 새로운 데이터 추가 및 중복 검사
│   ├── jdbcStudy06.java  // 특정 데이터를 검색하는 기능
│   ├── jdbcStudy07.java  // CRUD 기능을 갖춘 사용자 관리 시스템
│
├── basic/util/
│   ├── DBUtil.java  // 데이터베이스 연결 및 로그 관리
│
resources/
├── db.properties  // 데이터베이스 연결 정보
├── log4j.properties  // 로그 설정 파일
```

## 주요 기능

1. **JDBC 연습**
    - Oracle DB와 연결 및 SQL 실행
    - `Statement` vs `PreparedStatement` 비교
    - SELECT, INSERT, UPDATE, DELETE 문 실행
    - 예외 처리 및 리소스 반환 처리
2. **Log4j 연습**
    - `log4j.properties` 설정을 통해 콘솔 및 파일에 로그 출력
    - `INFO`, `DEBUG`, `ERROR` 레벨별 로그 관리
    - `DBUtil.java`에 로그 적용하여 DB 연결 상태 로깅

## 실행 방법

1. **데이터베이스 설정**
    - `db.properties` 파일에서 Oracle DB 접속 정보를 설정
    - 샘플 테이블 생성 (`LPROD`, `BANKINFO`, `MYMEMBER`)
2. **프로젝트 빌드 및 실행**
    
    ```
    javac -d bin src/basic/**/*.java
    java -cp bin basic.jdbcStudy.jdbcStudy01
    ```
    
## 데이터베이스 테이블 (예제)

```sql
CREATE TABLE LPROD (
    LPROD_ID NUMBER PRIMARY KEY,
    LPROD_GU VARCHAR2(10) UNIQUE NOT NULL,
    LPROD_NAME VARCHAR2(100) NOT NULL
);

CREATE TABLE BANKINFO (
    BANK_NO VARCHAR2(20) PRIMARY KEY,
    BANK_NAME VARCHAR2(50) NOT NULL,
    BANK_USER_NAME VARCHAR2(50) NOT NULL,
    BANK_DATE DATE DEFAULT SYSDATE
);

CREATE TABLE MYMEMBER (
    MEM_ID VARCHAR2(50) PRIMARY KEY,
    MEM_PASS VARCHAR2(100) NOT NULL,
    MEM_NAME VARCHAR2(100) NOT NULL,
    MEM_TEL VARCHAR2(20),
    MEM_ADDR VARCHAR2(255)
);
```
