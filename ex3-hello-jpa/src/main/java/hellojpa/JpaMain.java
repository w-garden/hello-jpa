package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
//            JPQL_기본(em);
//            Criteria_기본(em);
//            NativeSQL(em);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
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
