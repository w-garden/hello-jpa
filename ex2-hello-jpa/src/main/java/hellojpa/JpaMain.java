package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        //EntityManagerFactory은 애플리케이션당 1개만 있어야함.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //EntityManager 여러개 생성가능 : DatabaseConnection 받는것과 비슷함
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /*
             //연관관계가 없음
            //팀저장
            Team01 team =new Team01();
            team.setName("TeamA");
            em.persist(team);
            //회원저장
            Member01 member = new Member01();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
            //조회
            Member01 findMember = em.find(Member01.class, member.getId());


            Long findTeamId = findMember.getTeamId();
            Team01 findTeam = em.find(Team01.class, findTeamId);
             */

            //양방향 매핑 : 연관관계의 주인에 값 설정
            //권장사항 : 양쪽에 값 설정을 다 하자 -> 연관관계 편의 메서드로 처리
            //팀저장

            /*
            Team01 team =new Team01();
            team.setName("TeamA");
            em.persist(team);

            //회원저장
            Member01 member = new Member01();
            member.setUsername("member1");
//            member.changeTeam(team); //team 값설정  -> 연관관계 편의 메서드로 처리
            em.persist(member);
//            team.getMembers().add(member); //member 값설정 -> 연관관계 편의 메서드로 처리
            team.addMember(member);
//            em.flush();
//            em.clear();

            //조회(양방향 조회)
            Member01 findMember = em.find(Member01.class, member.getId());

            //Member01 -> Team01
            Team01 findTeam = findMember.getTeam01();
            System.out.println("findTeam =" + findTeam.getName());
            System.out.println("=========================");
            Team01 findTeam2 = em.find(Team01.class, team.getId());
            List<Member01> members2 = findTeam2.getMembers();
            for (Member01 m2 : members2) {
                System.out.println("m2 = " + m2.getUsername());
            }
            System.out.println("=========================");
            //Team01 -> Member01
            List<Member01> members = findMember.getTeam01().getMembers();
            for (Member01 m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=========================");
*/

/*

            // 1 : 다 연관관계

            Member01 member = saveMember(em);

            Team01 team = new Team01();
            team.setName("teamA");

            team.getMembers().add(member);

            em.persist(team);

            */
            tx.commit();
        } catch (Exception e) {

        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory를 닫아주어야한다.
    }

    private static Member01 saveMember(EntityManager em) {
        Member01 member = new Member01();
        member.setUsername("member1");
        em.persist(member);
        return member;
    }
}
