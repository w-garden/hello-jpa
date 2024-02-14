# ex1-hello-jpa

- JPA 기본 사용법

### 프로젝트 구조

- src/main
  - java
    - jpabook
      - start
  - resources
    - META-INF
    - SQL
- pom.xml


    Jpa basic Method
    회원 등록, 회원 찾기, 회원 수정, 회원 삭제, 특정 회원 조회
     - addMember(em);    // 회원 등록 : em.persist()
     - findMember(em);   // 회원 찾기 : em.find()
     - updateMember(em); // 회원 수정
     - deleteMember(em); // 회원 삭제 :  em.remove()
     - useJpql(em);      // 특정 회원 조회

    영속성 컨텍스트
    1차캐시, 동일성 보장, 쓰기 지연, 더티 체킹, 지연 로딩
     
    - persistenceEntity(em); //영속성 상태의 동일성 보장
    - dirtyChecking(em); //더티 체킹
    - flushEntity(em);

