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

            Team team =new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);

            member.setTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

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

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
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
        em.createNativeQuery("select * from MEMBER").getResultList();
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
