package jpabook.start_v1;

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
        User user = new User();
        user.setId(id);
        user.setUsername("Andy");
        user.setAge(2);

        //등록
        em.persist(user);

        //수정
        user.setAge(35);

        //한건 조회
        User findUser = em.find(User.class, id);
        System.out.println("findUser = " + findUser.getUsername() + ", age = " + findUser.getAge());

        //목록 조회
        List<User> users = em.createQuery("select m from Board m", User.class).getResultList();
        System.out.println("users.size() = " + users.size());

        //삭제
        em.remove(users);
    }


    private static void logic2(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";

        User memberA = new User();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        User memberB = new User();
        memberB.setId(id2);
        memberB.setUsername("Tom");
        memberB.setAge(35);

        //등록
        em.persist(memberA);
        em.persist(memberB);

        User findMemberA = em.find(User.class,"id1");
        User findMemberB = em.find(User.class,"id2");

        System.out.println(findMemberA == findMemberA);

    }
    private static void logic3(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";
        User memberA = new User();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        User memberB= new User();
        memberB.setId(id2);
        memberA.setUsername("Tom");
        memberA.setAge(30);

        //등록
        em.persist(memberA);
        em.persist(memberB);

        User findMemberA = em.find(User.class,"id1");
        User findMemberB = em.find(User.class,"id2");
        System.out.println(findMemberA == findMemberA);



    }

    private static void logic4(EntityManager em) {
        String id1 = "id1";
        String id2 = "id2";
        User memberA = new User();
        memberA.setId(id1);
        memberA.setUsername("Andy");
        memberA.setAge(30);

        User memberB= new User();
        memberB.setId(id2);
        memberB.setUsername("Tom");
        memberB.setAge(34);

        //등록
        em.persist(memberA);
        em.persist(memberB);


//        em.clear();




    }
}