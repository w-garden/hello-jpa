package jpql.example;


import jpql.Member;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            saveMember(em);
            basicJPQL(em);
            parameterBinding(em);


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void saveMember(EntityManager em) {
        Team team = new Team();
        team.setName("team1");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("member1");
        member1.setAge(30);
        member1.setTeam(team);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setAge(20);
        member2.setTeam(team);

        em.persist(member1);
        em.persist(member2);
    }

    private static void basicJPQL(EntityManager em) {
        List<Member> result = em.createQuery(
                "select m from Member m", Member.class
        ).getResultList();

        System.out.println("=============== basicJPQL =================");
        for (Member member : result) {
            System.out.println(member);
        }
        System.out.println("=============== basicJPQL =================");
    }
    private static void parameterBinding(EntityManager em) {
        Member result = em.createQuery("select m from Member m where m.username= :username", Member.class)
                .setParameter("username", "member2")
                .getSingleResult();
        System.out.println("===============parameterBinding.Named parameters=================");
        System.out.println("singleResult1 = " + result.getUsername());
        System.out.println("===============parameterBinding.Named parameters=================");

        Member result1 = em.createQuery("select m from Member m where m.username= ?1", Member.class)
                .setParameter(1, "member1")
                .getSingleResult();
        System.out.println("===============parameterBinding.Positional parameters==============");
        System.out.println("singleResult2 = " + result1.getUsername());
        System.out.println("===============parameterBinding.Positional parameters==============");
    }


}
