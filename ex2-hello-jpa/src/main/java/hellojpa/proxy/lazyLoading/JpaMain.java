package hellojpa.proxy.lazyLoading;


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
            Member_Lazy member = new Member_Lazy();
            member.setName("member1");

            Team_Lazy team = new Team_Lazy();
            team.setName("team1");
            team.addMember(member);
            em.persist(team);

            member.setTeam(team);
            em.persist(member);
            tx.commit();
            em.clear();

            tx.begin();
            System.out.println("=============== LAZY LOADING MEMBER 조회 =============");

            Member_Lazy findMember = em.find(Member_Lazy.class, 2L); //LAZY LOADING -> MEMBER 쿼리 실행
            System.out.println("=============== LAZY LOADING MEMBER 조회 =============");

            Team_Lazy findTeam = findMember.getTeam();

            System.out.println("=============== LAZY LOADING TEAM 조회 ===============");
            System.out.println("findTeam.getName() = " + findTeam.getName()); //실제 엔티티 사용시 조회
            System.out.println("=============== LAZY LOADING TEAM 조회 ===============");


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
