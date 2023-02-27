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

            for(int i =0; i<100; i++){
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }

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
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();
            System.out.println("result.size = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
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
