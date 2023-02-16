package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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
            Team team =new Team();
            team.setName("TeamA");
            em.persist(team);
            //회원저장
            Member member = new Member();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
            //조회
            Member findMember = em.find(Member.class, member.getId());


            Long findTeamId = findMember.getTeamId();
            Team findTeam = em.find(Team.class, findTeamId);
             */

            //양방향 매핑 : 연관관계의 주인에 값 설정
            //권장사항 : 양쪽에 값 설정을 다 하자 -> 연관관계 편의 메서드로 처리
            //팀저장

            /*
            Team team =new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원저장
            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team); //team 값설정  -> 연관관계 편의 메서드로 처리
            em.persist(member);
//            team.getMembers().add(member); //member 값설정 -> 연관관계 편의 메서드로 처리
            team.addMember(member);
//            em.flush();
//            em.clear();

            //조회(양방향 조회)
            Member findMember = em.find(Member.class, member.getId());

            //Member -> Team
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam =" + findTeam.getName());
            System.out.println("=========================");
            Team findTeam2 = em.find(Team.class, team.getId());
            List<Member> members2 = findTeam2.getMembers();
            for (Member m2 : members2) {
                System.out.println("m2 = " + m2.getUsername());
            }
            System.out.println("=========================");
            //Team -> Member
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=========================");
*/



            // 1 : 다 연관관계

            Member member = saveMember(em);

            Team team = new Team();
            team.setName("teamA");

            team.getMembers().add(member);

            em.persist(team);
            tx.commit();
        } catch (Exception e) {

        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory를 닫아주어야한다.
    }

    private static Member saveMember(EntityManager em) {
        Member member = new Member();
        member.setUsername("member1");
        em.persist(member);
        return member;
    }
}
