package jpabook.start_v3;

import jpabook.start_v1.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            addMember(em);
//            updateRelation(em);
//            deleteRelation(em);
//            deleteEntity(em);
            biDirection(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();
        System.out.println("members.size()="+ members.size());
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
        }
    }

    private static void deleteEntity(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        Member member2 = em.find(Member.class, "member2");
//        member1.setTeam(null);
        member2.setTeam(null);
        Team team1 = em.find(Team.class, "team1");
        Team team2 = em.find(Team.class, "team2");
        em.remove(team1);
        em.remove(team2);
    }

    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    private static void addMember(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);


        Member member1 = new Member("member1", "멤버1");
        Member member2 = new Member("member2", "멤버2");
        member1.setTeam(team1);
        member2.setTeam(team1);
        em.persist(member1);
        em.persist(member2);
    }

    private static void updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);

    }

    private static void logic(EntityManager em) {
        String id = "id1";
        User user = new User();
        user.setId(id);
        user.setUsername("Andy");
        user.setAge(2);

        //등록
        em.persist(user);

        //수정
        user.setAge(35);

        //한건 조회
        User findUser = em.find(User.class, id);
        System.out.println("findUser = " + findUser.getUsername() + ", age = " + findUser.getAge());

        //목록 조회
        List<User> users = em.createQuery("select m from Board m", User.class).getResultList();
        System.out.println("users.size() = " + users.size());

        //삭제
        em.remove(users);
    }

}
