package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
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
            countQuery ="select count (m) from Member m" )
    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용

//    Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용안함
//    List<Member> findByAge(int age, Pageable pageable);  //count 쿼리 사용안함
}
