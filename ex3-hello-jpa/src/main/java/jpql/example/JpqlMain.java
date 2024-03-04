package jpql.example;


import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            typeQuery(em);
            parameterBinding(em);
            projection(em);
            paging(em);
            join(em);
            fetchJoin(em);


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }




    private static void typeQuery(EntityManager em) {
        TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m", Member.class);
        List<Member> resultList = query1.getResultList();

        System.out.println("=============== typeQuery.query1 ==============");
        for (Member member : resultList) {
            System.out.println("member = " + member);
        }
        System.out.println("=============== typeQuery.query1 ==============");

        TypedQuery<String> query2 = em.createQuery("select m.username  from Member m", String.class);
        List<String> resultList2 = query2.getResultList();

        System.out.println("=============== typeQuery.query2 ==============");
        for (String string : resultList2) {
            System.out.println(string);
        }
        System.out.println("=============== typeQuery.query2 ==============");
        Query query3 = em.createQuery("select m.username, m.age from Member m");
        List resultList3 = query3.getResultList();

        System.out.println("=============== typeQuery.query3 ==============");
        for (Object o : resultList3) {
            Object[] result = (Object[]) o;
            System.out.println("name = " + result[0] + ", age = " + result[1]);
        }
        System.out.println("=============== typeQuery.query3 ==============");

        TypedQuery<Member> query4 = em.createQuery("select m FROM Member m WHERE username = :username", Member.class);
        Member resultList4 = query4.setParameter("username", "member1").getSingleResult();
        System.out.println("=============== typeQuery.query4 ==============");
        System.out.println(resultList4);
        System.out.println("=============== typeQuery.query4 ==============");

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

    private static void projection(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        Member findMember = result.get(0);
        findMember.setAge(20);

        em.createQuery("select o.address from Order o", Address.class)
                .getResultList();
        em.createQuery("select distinct m.username, m.age from Member m")
                .getResultList();
        List<MemberDTO> resultDto = em.createQuery("select new jpql.MemberDTO(m.username, m.age)  from Member m", MemberDTO.class)
                .getResultList();

        MemberDTO memberDTO = resultDto.get(0);
        System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
        System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
    }

    private static void paging(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m order by m.id", Member.class)
                .setFirstResult(10)
                .setMaxResults(20)
                .getResultList();
        System.out.println("=================== paging ===================");
        System.out.println("result.size = " + result.size());
        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
        System.out.println("=================== paging ===================");
    }

    private static void join(EntityManager em) {
        System.out.println("================= innerJoin ================");
        List<Object[]> innerJoin = em.createQuery("select m, t  from Member m inner join m.team t")
                .setMaxResults(2)
                .getResultList();
        for (Object[] row : innerJoin) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
            System.out.println(member);
            System.out.println(team);
        }
        System.out.println("================= innerJoin ================");
        System.out.println("================= outerJoin ================");
        List<Object[]> outerJoin = em.createQuery("select m, t  from Member m, Team t where m.username = t.name")
                .setMaxResults(2)
                .getResultList();
        for (Object[] row : outerJoin) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
            System.out.println(member);
            System.out.println(team);
        }
        System.out.println("================= outerJoin ================");
        System.out.println("================= joinOn ================");
        List<Object[]> joinOn = em.createQuery("select m, t  from Member m left join m.team t on t.name = 'teamA'")
                .setMaxResults(2)
                .getResultList();
        for (Object[] row : joinOn) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
            System.out.println(member);
            System.out.println(team);
        }
        System.out.println("================= joinOn ================");
        System.out.println("================= nonRelationJoin ================");

        Member findMember = em.find(Member.class, 1L);
        findMember.setUsername("team1");
        List<Object[]> nonRelationJoin = em.createQuery("select m, t from Member m left join Team t on m.username = t.name")
                .setMaxResults(2)
                .getResultList();
        for (Object[] row : nonRelationJoin) {
            Member member = (Member) row[0];
            Team team = (Team) row[1];
            System.out.println(member);
            System.out.println(team);
        }
        System.out.println("================= nonRelationJoin ================");
    }

    private static void fetchJoin(EntityManager em) {
        Team teamA = new Team();
        teamA.setName("팀A");
        em.persist(teamA);

        Team teamB = new Team();
        teamB.setName("팀B");
        em.persist(teamB);

        Team teamC = new Team();
        teamC.setName("팀C");
        em.persist(teamC);


        Member member1 = new Member();
        member1.setUsername("회원1");
        member1.setTeam(teamA);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("회원2");
        member2.setTeam(teamA);
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("회원3");
        member3.setTeam(teamB);
        em.persist(member3);

        Member member4 = new Member();
        member4.setUsername("회원4");
        em.persist(member4);

        em.flush();
        em.clear();

        System.out.println("================== entity fetchJoin ===================");
        List<Member> result =
                em.createQuery("select m From Member m join fetch m.team where m.username LIKE '회원%'", Member.class)
                .getResultList();
        for (Member member : result) {
            System.out.println("username = " + member.getUsername() + ", teamName = " + member.getTeam().getName());
        }
        System.out.println("================== entity fetchJoin ===================");

        System.out.println("================== collection fetchJoin ===================");
        List<Team> result2 =
                em.createQuery("select distinct t From Team t join fetch t.members where t.name LIKE '팀%'", Team.class)
                .getResultList();
        for (Team team : result2) {
            System.out.println("team = " + team.getName() + " | members = " + team.getMembers().size());
            for (Member member : team.getMembers()) {
                System.out.println("-> member = " + member);
            }
        }
        System.out.println("================== collection fetchJoin ===================");
    }

}
