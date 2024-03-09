import jpql.Member;
import jpql.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import javax.persistence.criteria.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import static jpql.MemberType.USER;

public class CriteriaTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @BeforeEach
    public void savedMemberAndTeam() {
        tx.begin();

        Team team = new Team();
        team.setName("팀A");
        em.persist(team);

        for (int i = 1; i <= 20; i++) {
            Member member = new Member();
            member.setUsername("회원" + i);
            member.setAge((int) (Math.random() * 50) + 1);
            member.setType(USER);
            member.setTeam(team);
            em.persist(member);
        }
        em.clear();
    }

    @AfterEach
    public void commit() {
        tx.commit();
    }

    @Test
    public void CriteriaBasic() {
        CriteriaBuilder cb = em.getCriteriaBuilder(); //Criteria 쿼리 빌더

        CriteriaQuery<Member> cq = cb.createQuery(Member.class); //Criteria 생성, 반환 타입 지정

        Root<Member> m = cq.from(Member.class); //from 절
        cq.select(m); //select 절

        TypedQuery<Member> query1 = em.createQuery(cq);

        System.out.println("==============      query1      ==============");
        List<Member> resultList = query1.getResultList();
        for (Member member : resultList) {
            assertThat(member.getUsername(), startsWith("회원"));
        }

        Predicate usernameEqual = cb.like(m.get("username"), "회원%");
        Order ageDesc = cb.desc(m.get("age"));

        cq.select(m).where(usernameEqual).orderBy(ageDesc);
        TypedQuery<Member> query2 = em.createQuery(cq);

        System.out.println("==============      query2      ==============");
        List<Member> resultList1 = query2.getResultList();
        for (Member member : resultList1) {
            assertThat(member.getUsername(), startsWith("회원"));
            System.out.print(member.getAge() + " ");
        }


//        Predicate ageGT = cb.greaterThan(m.<Integer>get("age"), 20);
        Predicate ageGT = cb.greaterThan(m.<Integer>get("age"), 20);

        cq.select(m);
        cq.where(ageGT);
        cq.orderBy(cb.desc(m.get("age")));

        TypedQuery<Member> query3 = em.createQuery(cq);

        System.out.println("==============      query3      ==============");
        List<Member> resultList2 = query3.getResultList();
        for (Member member : resultList2) {
            assertThat(member.getAge(), greaterThan(20));
        }

    }


}
