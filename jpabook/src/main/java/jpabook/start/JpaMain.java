package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //엔티티 매니저 팩토리 - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        //엔티티 매니저 - 생성
        EntityManager em = emf.createEntityManager();
        //트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
//            logic(em);
            logic4(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("Andy");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(35);

        //한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember = " + findMember.getUsername() + ", age = " + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members.size() = " + members.size());

        //삭제
        em.remove(members);
    }


    private static void logic2(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";

        Member memberA = new Member();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        Member memberB = new Member();
        memberB.setId(id2);
        memberB.setUsername("Tom");
        memberB.setAge(35);

        //등록
        em.persist(memberA);
        em.persist(memberB);

        Member findMemberA = em.find(Member.class,"id1");
        Member findMemberB = em.find(Member.class,"id2");

        System.out.println(findMemberA == findMemberA);

    }
    private static void logic3(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";
        Member memberA = new Member();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        Member memberB= new Member();
        memberB.setId(id2);
        memberA.setUsername("Tom");
        memberA.setAge(30);

        //등록
        em.persist(memberA);
        em.persist(memberB);

        Member findMemberA = em.find(Member.class,"id1");
        Member findMemberB = em.find(Member.class,"id2");
        System.out.println(findMemberA == findMemberA);



    }

    private static void logic4(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";
        Member memberA = new Member();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        Member memberB= new Member();
        memberB.setId(id2);
        memberB.setUsername("Tom");
        memberB.setAge(34);

        //등록
        em.persist(memberA);
        em.persist(memberB);


//        em.clear();




    }
}
