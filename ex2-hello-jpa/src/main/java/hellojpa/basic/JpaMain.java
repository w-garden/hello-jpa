package hellojpa.basic;

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
            notMapping(em);
            System.out.println("=================== COMMIT ===================");
            tx.commit();
            System.out.println("=================== COMMIT ===================\n");
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void notMapping(EntityManager em) {
        Team1 team = new Team1();
        team.setName("TeamA");
        em.persist(team);

        Member1 member = new Member1();
        member.setUsername("member1");
        member.setTeamId(team.getId());
        em.persist(member);

        Member1 findMember = em.find(Member1.class, member.getId());

        Long findTeamId = findMember.getTeamId();
        Team1 findTeam = em.find(Team1.class, findTeamId);
        System.out.println("================  notMapping  ==================");
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("member.getTeamId() = " + member.getTeamId());
        System.out.println("findTeam.getName() = " + findTeam.getName());
        System.out.println("================  notMapping  ==================\n");


    }









}
