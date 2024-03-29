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
            NativeSQL(em);


            JPQL타입표현_ENUM(em);




            /**
             * 벌크연산
             * -벌크연산은 영속성 컨텍스트를 무시하고 DB에 직접 쿼리한다.
             * 해결방법
             * 1. 벌크 연산을 먼저 실행
             * 2. 벌크 연산 수행 후 영속성 컨텍스트 초기화
             */
            벌크연산(em);


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void 벌크연산(EntityManager em) {
        for(int i=0; i<15; i++){
            Member member = new Member();
            member.setUsername("회원"+i);
            em.persist(member);
        }
        Member member = new Member();
        member.setUsername("테스트 회원");
        em.persist(member);
        //FLUSH 자동호출
        //벌크연산은 DB에 바로 반영
        int result = em.createQuery("update Member m set m.age=20") 
                .executeUpdate();
        em.clear(); //clear를 해야 다음 쿼리가 제대로 조회됨
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("findMember = " + findMember);
        System.out.println("result = " + result);
    }









    private static void JPQL타입표현_ENUM(EntityManager em) {
        String query = "select m.username, 'HELLO', true From Member m where m.type=:userType";
        List<Object[]> result = em.createQuery(query)
                .setParameter("userType", MemberType.ADMIN)
                .getResultList();
        for (Object[] objects : result) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[0] = " + objects[1]);
            System.out.println("objects[0] = " + objects[2]);

        }
    }










    private static void NativeSQL(EntityManager em) {
        em.createNativeQuery("select * from Member").getResultList();
    }




}
