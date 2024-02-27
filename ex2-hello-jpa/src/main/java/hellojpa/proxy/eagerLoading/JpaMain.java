package hellojpa.proxy.eagerLoading;

import hellojpa.basicMapping.Member2;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Member_Eager member = new Member_Eager();
            member.setName("member1");

            Team_Eager team = new Team_Eager();
            team.setName("team1");
            em.persist(team);

            member.setTeam(team);
            em.persist(member);
            tx.commit();
            em.clear();

            tx.begin();
            System.out.println("=============== EAGER LOADING ===============");
            Member_Eager findMember = em.find(Member_Eager.class, 1L); //EAGER LOADING -> 쿼리 실행
            System.out.println("=============== EAGER LOADING ===============");

            Team_Eager findTeam = findMember.getTeam();


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
