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
    }



    @Test
    public void basicJPQL() {
        System.out.println("==================================           basicJPQL 실행          ==================================");
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(result.size(),equalTo(20));
        final Member member = result.get(0);
        assertThat(member.getUsername(),equalTo("회원1"));
    }

    @AfterEach
    public void commit() {
        tx.commit();
    }
}
