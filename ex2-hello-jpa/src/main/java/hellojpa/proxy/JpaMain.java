package hellojpa.proxy;

import hellojpa.basicMapping.Member2;
import hellojpa.embedded.Address;
import hellojpa.embedded.Member;
import hellojpa.embedded.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            /**
             * 프록시 객체의 특징
             * 1. 프록시 객체는 처음 사용할 때 한번만 초기화
             * 2. 프록시 객체를 초기화할때, 프록시 객체가 실제 엔티티로 바뀌는 것이 아니다.
             * 3. 프록시 객체는 원본 엔티티를 상속 받음 (따라서 객체 비교시에는 instance of 를 사용해야함)
             * 4. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
             * 5. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제발생
             */

            Member2 member = new Member2();
            member.setUsername("proxy_member1");
            em.persist(member);

            tx.commit(); //프록시 객체 테스트 위해 커밋
            em.clear();

            
            tx.begin();
            proxy(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }



    private static void proxy(EntityManager em) {
        Member2 findMember = em.getReference(Member2.class, 1L); //DB 쿼리 실행 안함
        System.out.println("================= proxy =======================");
        System.out.println("findMember.getClass() = " + findMember.getClass());  //프록시(가짜) 클래스 class hellojpa.basicMapping.Member2$HibernateProxy$FJSqNGTD
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getUsername() = " + findMember.getUsername()); //실제 데이터 조회
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("================= proxy =======================");

    }
}
