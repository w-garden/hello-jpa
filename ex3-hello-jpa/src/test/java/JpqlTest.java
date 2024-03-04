import jpql.Member;
import jpql.Team;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static jpql.MemberType.USER;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class JpqlTest {

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


    @Test
    public void basicJPQL() {
        tx.begin();
        System.out.println("==================================           basicJPQL 실행          ==================================");
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(result.size(), equalTo(20));
        final Member member = result.get(0);
        assertThat(member.getUsername(), equalTo("회원1"));
    }

    @Test
    public void typeQuery() {

        System.out.println("==================================           typeQuery.query1          ==================================");
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        List<Member> resultList = query1.getResultList();
        assertThat(resultList.size(), equalTo(20));

        em.clear();
        System.out.println("==================================           typeQuery.query2          ==================================");
        TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
        List<String> resultList2 = query2.getResultList();

        for (String string : resultList2) {
            assertThat(string, containsString("회원"));
        }
        em.clear();

        System.out.println("==================================           typeQuery.query3          ==================================");
        Query query3 = em.createQuery("select m.username, m.age, m.team.name from Member m");
        List resultList3 = query3.getResultList();

        for (Object o : resultList3) {
            Object[] result = (Object[]) o;
            assertThat((String) result[0], containsString("회원"));
            assertThat((int) result[1], lessThan(50));
            assertThat((String) result[2], containsString("팀A"));

        }
        System.out.println("=============== typeQuery.query4 ==============");
        em.clear();
//        TypedQuery<Member> query4 = em.createQuery("select m FROM Member m WHERE username = :username", Member.class);
//        Member resultList4 = query4.setParameter("username", "member1").getSingleResult();
//        System.out.println(resultList4);

    }

    @AfterEach
    public void commit() {
        tx.commit();
    }
}
