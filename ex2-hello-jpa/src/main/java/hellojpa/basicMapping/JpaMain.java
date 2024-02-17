package hellojpa.basicMapping;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            /*
             *   //양방향 매핑 : 연관관계의 주인에 값 설정
             *   //권장사항 : 양쪽에 값 설정을 다 하자 -> 연관관계 편의 메서드로 처리
             */
            testSave(em);
            selectTeamName(em);
            queryLogicJoin(em);
            updateRelation(em);
            queryLogicJoin(em);

            biDirection(em);

            testSaveNonOwner(em);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void testSaveNonOwner(EntityManager em) {
        Member2 member1 = new Member2();
        member1.setUsername("회원D");
        em.persist(member1);

        Member2 member2 = new Member2();
        member2.setUsername("회원E");
        em.persist(member2);

        Team2 team = new Team2();
        team.setName("팀C");
        team.getMembers().add(member1);
        team.getMembers().add(member2);
        em.persist(team);
        //SELECT * FROM Member3 쿼리로 확인

    }

    private static void biDirection(EntityManager em) {
        Team2 team2 = em.find(Team2.class, 1L);
        List<Member2> members = team2.getMembers();
        System.out.println("============= biDirection ============");
        for (Member2 member : members) {
            System.out.println("member.getUsername() = " + member.getUsername());
        }
        System.out.println("============= biDirection ============\n");
    }

    private static void updateRelation(EntityManager em) {
        Team2 team2 = new Team2();
        team2.setName("팀B");
        em.persist(team2);
        Member2 member = em.find(Member2.class, 3L);
        member.setTeam(team2);
    }

    private static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member3 m join m.team t where t.name = :teamName";
        List<Member2> resultList = em.createQuery(jpql, Member2.class)
                .setParameter("teamName", "팀A")
                .getResultList();
        System.out.println("=========== queryLogicJoin =================");
        for (Member2 member2 : resultList) {
            System.out.println("[query] member2.getUsername() = " + member2.getUsername());
        }
        System.out.println("=========== queryLogicJoin =================\n");
    }

    private static void testSave(EntityManager em) {

        //팀A 저장
        Team2 teamA = new Team2();
        teamA.setName("팀A");
        em.persist(teamA);

        //회원A 저장
        Member2 memberA = new Member2();
        memberA.setUsername("회원A");
        memberA.setTeam(teamA); //연관관계 설정 memberA => teamA
        em.persist(memberA);

        //회원B 저장
        Member2 memberB = new Member2();
        memberB.setUsername("회원B");
        memberB.setTeam(teamA); //연관관계 설정 memberB => teamA
        em.persist(memberB);

        //회원C 저장
        Member2 memberC = new Member2();
        memberC.setUsername("회원C");
        memberC.setTeam(teamA); //연관관계 설정 memberB => teamA
        em.persist(memberC);

    }

    private static void selectTeamName(EntityManager em) {
        Member2 findMember = em.find(Member2.class, 2L);
        Team2 team = findMember.getTeam();
        System.out.println("======== select Team4 ==========");
        System.out.println("팀 이름 = " + team.getName());
        System.out.println("======== select Team4 ==========");
    }


}
