package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
//            JPQL_기본(em);
//            Criteria_기본(em);
//            NativeSQL(em);
/**
            //Fetch Join 예제에서는 사용안함
            Team team =new Team();
            team.setName("teamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(20);

            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();
*/
//            타입쿼리(em);
//            파라미터바인딩(em);
            /**
             * 프로젝션
             * - select 절에 조회할 대상을 지정하는 것
             * - 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문다으 기본 데이터 타입)
             * 1. SELECT m FROM Member m -> 엔티티 프로젝션
             * 2. SELECT m.team FROM Member m ->엔티티 프로젝션
             * 3. SELECT m.address FROM Member m -> 입메디드 타입 프로젝션
             * 4. SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
             */
//            프로젝션(em);

            /**
             * 페이징
             * 1. setFirstResult()
             * 2. setMaxResult
             */
//            페이징(em);
            /**
             * 조인
             * 1. 내부조인 : SELECT m FROM Member m [INNER] JOIN m.team t
             * 2. 외부조인 : SELECT m FROM Member m LEFT JOIN m.team t
             * 3. 세타조인 : SELECT count(m) from Member m, Team t where m.username = t.name
             */


//            조인(em);
//            JPQL타입표현_ENUM(em);
//            조건식(em);
            /**
             * JPQL 기본함수
             * CONCAT, SUBSTRING, TRIM
             * LOWER, UPPER, LENGTH, LOCATE, ABS, SQRT, MOD
             * SIZE, INDEX(JPA용도)
             */
//            기본함수(em);
            /**
             * Fetch Join
             * - 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회 - 성능최적화
             * - 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
             * - 예)
             *     JPQL : select m from Member m join fetch m.team
             *     SQL  : SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID=T.ID
             */

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

            String query1 = "select m From Member m"; //1+n 쿼리가 실행됨
            String query2 = "select m From Member m join fetch m.team"; //엔티티 페치조인
            String query3 = "select distinct t From Team t join fetch t.members"; //컬렉션 페치조인
            String query4 = "select distinct t From Team t join fetch t.members where t.name='팀A'"; //컬렉션 페치조인

            /*
            //엔티티 페치조인
            List<Member> result = em.createQuery(query2, Member.class)
                    .getResultList();
            for (Member member : result) {
                System.out.println("username = " + member.getUsername() + ", teamName = " + member.getTeam().getName());
            }*/

            //컬렉션 페치조인
            List<Team> result = em.createQuery(query3, Team.class)
                    .getResultList();
            for (Team team : result) {
                System.out.println("team = " + team.getName() + " | members = "+ team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void 기본함수(EntityManager em) {
        String query1 = "select 'a' || 'b' From Member m";
        String query2 = "select concat('a', 'b') From Member m";
        String query3 = "select locate('de', 'abcdef') From Member m";

        List<Integer> result = em.createQuery(query3, Integer.class).getResultList();
        for (Integer s : result) {
            System.out.println("s = " + s);
        }
    }

    private static void 조건식(EntityManager em) {
        //CASE
        String query1 = "select case when m.age <= 10 then '학생요금' when m.age>= 60  then '경로요금'" +
                " else '일반요금' end from Member m";
        //coalesce
        String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m";
        //NULLIF
        String query3 = "select nullif(m.username, '관리자')  as name from Member m";

        List<String> result = em.createQuery(query3, String.class).getResultList();
        for (String s : result) {
            System.out.println("s = " + s);

        }
    }

    private static void JPQL타입표현_ENUM(EntityManager em) {
        String query = "select m.username, 'HELLO', true From Member m where m.type=:userType";
        List<Object[]> result = em.createQuery(query)
                .setParameter("userType",MemberType.ADMIN)
                .getResultList();
        for (Object[] objects : result) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[0] = " + objects[1]);
            System.out.println("objects[0] = " + objects[2]);

        }
    }

    private static void 조인(EntityManager em) {
        //내부조인
        String qlString1 = "select m from Member m inner join m.team t";
        //외부조인
        String qlString2 = "select m from Member m, Team t where m.username=t.name";
        //ON 절을 이용한 조인
        String qlString3 = "select m from Member m left join m.team t on t.name='teamA'";
        //연관관계 없는 외부 조인
        String qlString4 = "select m from Member m left join Team t on m.username=t.name";

        List<Member> result = em.createQuery(qlString4, Member.class)
                .getResultList();
        System.out.println("result.size() = " + result.size());
    }

    private static void 페이징(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                .setFirstResult(10)
                .setMaxResults(20)
                .getResultList();
        System.out.println("result.size = " + result.size());
        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }
    }

    private static void 프로젝션(EntityManager em) {
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

    private static void 파라미터바인딩(EntityManager em) {
        Member result = em.createQuery("select m from Member m where m.username= :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        System.out.println("singleResult1 = " + result.getUsername());

        Member result1 = em.createQuery("select m from Member m where m.username= ?1", Member.class)
                .setParameter(1, "member1")
                .getSingleResult();
        System.out.println("singleResult2 = " + result1.getUsername());
    }

    private static void 타입쿼리(EntityManager em) {
        TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
        TypedQuery<String> query2 = em.createQuery("select m.username  from Member m", String.class);
        Query query3 = em.createQuery("select m.username, m.age from Member m");
    }

    private static void NativeSQL(EntityManager em) {
        em.createNativeQuery("select * from Member").getResultList();
    }

    private static void Criteria_기본(EntityManager em) {
        //Criteria 사용준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);
        CriteriaQuery<Member> cq = query.select(m);
        String username = "aaa";
        if (username != null) {
            cq = cq.where(cb.equal(m.get("username"), "kim"));
        }
        List<Member> resultList = em.createQuery(cq).getResultList();
    }

    private static void JPQL_기본(EntityManager em) {
        List<Member> re = em.createQuery(
                "select m from Member m where m.username like '%kim%'", Member.class
        ).getResultList();
    }
}
