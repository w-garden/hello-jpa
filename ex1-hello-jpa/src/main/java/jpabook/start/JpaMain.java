package jpabook.start;

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

        try {
            tx.begin();
            /*
             * Jpa basic Method
             * 회원 등록, 회원 찾기, 회원 수정, 회원 삭제, 특정 회원 조회
             */
            addMember(em);    // 회원 등록 : em.persist()
            findMember(em);   // 회원 찾기 : em.find()
            updateMember(em); // 회원 수정
            deleteMember(em); // 회원 삭제 :  em.remove()
            useJpql(em);      // 특정 회원 조회

            /*
             * 영속성 컨텍스트
             * 1차캐시, 동일성 보장, 쓰기 지연, 더티 체킹, 지연 로딩
             */
            persistenceEntity(em); //영속성 상태의 동일성 보장
            dirtyChecking(em); //더티 체킹
            flushEntity(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("에러발생");
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close(); //WAS 가 내려갈때 EntityManagerFactory를 닫아주어야한다.
    }

    private static void flushEntity(EntityManager em) {
        /*
         * 플러시발생 상황
         * 1. em.flush();
         * 2. 트랜잭션 commit
         * 3. 중간에 JPQL 실행 : 실제 SQL이 실행되기에
         */
        Member member = new Member(200L, "member200");
        em.persist(member);
        em.flush(); //insert 쿼리 실행*/


        Member member1 = new Member(201L, "member201");
        member1.setUsername("hello2");
        member1.setRoleType(RoleType.ADMIN);
        em.persist(member1);
        System.out.println("member.id = "+ member1.getId());
    }

    private static void dirtyChecking(EntityManager em) {
        Member member = em.find(Member.class, 1L);
        member.setUsername("dirtyCheck 테스트");
        Member findMember = em.find(Member.class, 1L);
        System.out.println("====== dirty checking ======");
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
        System.out.println("member.getUsername() = " + member.getUsername());
        System.out.println("====== dirty checking ======");
    }

    private static void persistenceEntity(EntityManager em) {
        //비영속(Nonpersistence)
        Member member1 = new Member(101L, "nonPersistence");
        Member member2 = new Member(101L, "nonPersistence");
        System.out.println("==================nonPersistence===============");
        System.out.println("(member1 == member2) = " + (member1 == member2));
        System.out.println("==================nonPersistence===============\n");

        //영속(persis)
        em.persist(member1);
        System.out.println("===================Persistence================");
        Member findMember1 = em.find(Member.class, 101L);
        Member findMember2 = em.find(Member.class, 101L);
        System.out.println("(findMember2 == findMember3) = " + (findMember1 == findMember2));
        System.out.println("===================Persistence================\n");
    }

    private static void useJpql(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                .setFirstResult(5)
                .setMaxResults(8)
                .getResultList();
        System.out.println("===================useJpql================");
        for (Member member : result) {
            System.out.println("member.name = " + member.getUsername());
        }
        System.out.println("===================useJpql================");

    }

    private static void deleteMember(EntityManager em) {
        Member deleteMember = em.find(Member.class, 2L);
        em.remove(deleteMember); //회원 삭제
        Member findMember = em.find(Member.class, 2L);

        System.out.println("===============deleteMember ===============");
        try {
            System.out.println("findMember.getUsername() = " + findMember.getUsername());
        } catch (NullPointerException e) {
            System.out.println("em.remove() 로 삭제 했기 때문에 java.lang.NullPointerException 발생 !!");
        }
        System.out.println("===============deleteMember ===============\n");


    }

    private static void updateMember(EntityManager em) {
        Member memberA = em.find(Member.class, 1L);
        memberA.setUsername("HelloJPA");
        memberA.setAge(46);
        Member findMember = em.find(Member.class, 1L);

        System.out.println("===============updateMember ===============");
        System.out.println("findMember.getUsername = " + findMember.getUsername());
        System.out.println("===============updateMember ===============\n");
    }

    private static void findMember(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("===============findMember ===============");
        System.out.println("id : " + findMember.getUsername());
        System.out.println("name : " + findMember.getId());
        System.out.println("===============findMember ===============\n");
    }

    private static void addMember(EntityManager em) {
        Member member1 = new Member(1L, "helloA");
        Member member2 = new Member(2L, "helloB");
        Member member3 = new Member(3L, "helloC");
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
    }

}
