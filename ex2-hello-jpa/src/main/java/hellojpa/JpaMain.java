package hellojpa;

import hellojpa.inheritance.Item;
import hellojpa.inheritance.Movie;

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
            Team02 team =new Team02();
            team.setName("TeamA");
            em.persist(team);
            //회원저장
            Member02 member = new Member02();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
            //조회
            Member02 findMember = em.find(Member02.class, member.getId());


            Long findTeamId = findMember.getTeamId();
            Team02 findTeam = em.find(Team02.class, findTeamId);
             */

            //양방향 매핑 : 연관관계의 주인에 값 설정
            //권장사항 : 양쪽에 값 설정을 다 하자 -> 연관관계 편의 메서드로 처리
            //팀저장

            /*
            Team02 team =new Team02();
            team.setName("TeamA");
            em.persist(team);

            //회원저장
            Member02 member = new Member02();
            member.setUsername("member1");
//            member.changeTeam(team); //team 값설정  -> 연관관계 편의 메서드로 처리
            em.persist(member);
//            team.getMembers().add(member); //member 값설정 -> 연관관계 편의 메서드로 처리
            team.addMember(member);
//            em.flush();
//            em.clear();

            //조회(양방향 조회)
            Member02 findMember = em.find(Member02.class, member.getId());

            //Member02 -> Team02
            Team02 findTeam = findMember.getTeam01();
            System.out.println("findTeam =" + findTeam.getName());
            System.out.println("=========================");
            Team02 findTeam2 = em.find(Team02.class, team.getId());
            List<Member02> members2 = findTeam2.getMembers();
            for (Member02 m2 : members2) {
                System.out.println("m2 = " + m2.getUsername());
            }
            System.out.println("=========================");
            //Team02 -> Member02
            List<Member02> members = findMember.getTeam01().getMembers();
            for (Member02 m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=========================");
*/

/*

            // 1 : 다 연관관계

            Member02 member = saveMember(em);

            Team02 team = new Team02();
            team.setName("teamA");

            team.getMembers().add(member);

            em.persist(team);

            */


            /**
             * 상속관계
             */
            Movie movie = new Movie();
            movie.setDirector("감독1");
            movie.setActor("배우1");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);
            em.persist(movie);

            em.flush();
            em.clear();

//            Movie findMovie = em.find(Movie.class, movie.getId());
           Item item = em.find(Item.class, movie.getId());
            System.out.println("item = "+ item);

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
