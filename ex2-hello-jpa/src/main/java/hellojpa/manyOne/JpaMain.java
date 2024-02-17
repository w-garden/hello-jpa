package hellojpa.manyOne;



import hellojpa.basicMapping.Member2;
import hellojpa.basicMapping.Team2;

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



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

//    private static void oneToMany(EntityManager em) {
//        Member3 member = saveMember(em);
//
//        Team3 team = new Team3();
//        team.setName("teamA");
//
//        team.getMembers().add(member);
//
//        em.persist(team);
//    }



}
