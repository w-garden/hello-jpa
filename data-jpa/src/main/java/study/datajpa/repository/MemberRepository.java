package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy(); //전체조회

    List<Member> findTop3HelloBy(); //3개조회

    @Query(name = "Member.findByUsername")
        //없어도 실행됨
    List<Member> findByUsername(@Param("username") String username); //NamedQuery 사용하기

    /**
     * 실무에서 많이씀
     */
    @Query("select m from Member m where m.username=:username and m.age=:age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findByUsernameList();

    @Query("select new study.datajpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDTO> findMemberDto();

    @Query("select m from Member m where  m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    /**
     * 반환 타입을 유연하게 작성 가능
     */
    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); //단건 Optional


    /**
     * 페이징과 정렬
     */
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count (m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용

//    Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용안함
//    List<Member> findByAge(int age, Pageable pageable);  //count 쿼리 사용안함

    /**
     * 벌크성 수정 쿼리
     */
    @Modifying(clearAutomatically = true) //쿼리 실행후 영속성 컨텍스트를 초기화함
    @Query("update Member m set m.age =m.age +1 where m.age>=:age")
    int bulkAgePlus(@Param("age") int age);

    /**
     * fetch join
     */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


    /**
     * Entity Graph
     * -fetch join 과 같은 효과
     * -fetch join 의 간편 버전
     */
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    /**
     * JPA Hint
     * 단순 db 값 조회하기 위해서 사용
     */
    @QueryHints(value = @QueryHint(name ="org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    /**
     * Lock
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);



}
