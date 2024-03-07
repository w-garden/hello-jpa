import jpql.Member;
import jpql.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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


}
