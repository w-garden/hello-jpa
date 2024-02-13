package start_v3;

import jpabook.start_v3.Member;
import jpabook.start_v3.Team;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class TestClass {
    EntityManager em = Persistence.createEntityManagerFactory("jpabook").createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @Test
    public void testSave() {
        try {
            tx.begin();

            Team team1 = new Team("team1", "팀1");
            em.persist(team1);

            Member member1 = new Member("Member1","회원1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member("Member2", "회원2");
            member2.setTeam(team1);
            em.persist(member2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

    }

}
