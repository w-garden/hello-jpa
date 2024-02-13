package jpabook.start;

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
 *   Jpa basic Method
 *   회원등록, 회원 찾기, 회원 수정, 회원 삭제, 특정 회원 조회
 */
            Member member = new Member();
            //회원 등록
            member.setId(2L);
            member.setUsername("helloB");
            em.persist(member);

            Member findMember = em.find(Member.class,1L);

            //회원 찾기
            System.out.println("id : " + findMember.getUsername());
            System.out.println("name : " + findMember.getId());

            /*
            //회원 수정
            findMember.setName("HelloJPA");*/

            /*
            //회원 삭제
            em.remove(findMember);*/

            /*
         // 특정 회원 조회
            List<Member> result =em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for(Member member : result){
                System.out.println("member.name = " + member.getName());
            }
            */


            /**
             * 영속성 컨텍스트
             * 1차캐시, 동일성보장, 쓰기 지연, 더티체킹, 지연 로딩
             */

            /*
            //비영속
            Member member = new Member(102L,"HelloJPA")

            System.out.println("=== BEFORE ===");
            //영속(persis)
           // em.persist(member);

            //준영속(detach)
           //  em.detach(member);
            System.out.println("=== AFTER ===");

            Member findMember2 = em.find(Member.class, 101L);
            Member findMember3 = em.find(Member.class, 101L);
            System.out.println("result = " +(findMember2 == findMember3));
             */

            /**
             * 쓰기 지연
             */
        /*

            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");
            em.persist(member1);
            em.persist(member2);
            System.out.println("==================================");
            */
            /**
             * 더티체킹
             */
          /*  Member member = em.find(Member.class, 2L);
            member.setUsername("dirtyCheck 테스트");*/

            /**
             * 플러시발생 상황
             * 1. em.flush();
             * 2. 트랜잭션 commit
             * 3. 중간에 JPQL 실행 : 실제 SQL이 실행되기에
             */
           /* Member member = new Member(200L, "member200");
            em.persist(member);

            em.flush(); //insert 쿼리 실행*/



//            Member member = new Member();
            member.setUsername("hello2");
            member.setRoleType(RoleType.ADMIN);
            System.out.println("===============================");

            em.persist(member);
            System.out.println("member.id = "+ member.getId());
            System.out.println("===============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory를 닫아주어야한다.
    }
}
