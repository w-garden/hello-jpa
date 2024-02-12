package jpabook.start_v1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        User member = createMember("userA", "회원1");
        member.setUsername("회원명변경");

        mergeMember(member);

    }


    private static User createMember(String id, String username) {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1= em1.getTransaction();

        tx1.begin();

        User user = new User();
        user.setId(id);
        user.setUsername(username);

        em1.persist(user);
        tx1.commit();

        em1.close(); //user 엔티티는 준영속 상태가 된다.

        return user;
    }
    private static void mergeMember(User user) {
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();

        System.out.println("user = " + em2.find(User.class,"userA").getUsername());

        User mergeUser = em2.merge(user);
        tx2.commit();

        //준영속 상태
        System.out.println("user = " + user.getUsername());

        //영속 상태
        System.out.println("mergeUser = " + mergeUser.getUsername());


        System.out.println("em2 contains user = " + em2.contains(user));
        System.out.println("em2 contains mergeUser = " + em2.contains(mergeUser));

        em2.close();

    }

}
