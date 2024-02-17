package hellojpa.oneMany;


import hellojpa.manyOne.Member3;
import hellojpa.manyOne.Team3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            testSave(em);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void testSave(EntityManager em) {
        Member4 member1 = new Member4("member1");
        Member4 member2 = new Member4("member2");

        Team4 team1 = new Team4("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1); //INSERT-member1
        em.persist(member2); //INSERT-member2
        em.persist(team1); //INSERT-team1, UPDATE-member1.fk, UPDATE-member2.fk
    }

    private static void oneToMany(EntityManager em) {
        Member3 member = new Member3();

        Team3 team = new Team3();
        team.setName("teamA");

        team.getMembers().add(member);

        em.persist(team);
    }


}
