package hellojpa.nonIdentifying;

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
            usingIdClass(em);
            usingEmbeddedId(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }

    private static void usingEmbeddedId(EntityManager em) {

        Parent2 parent = new Parent2(new ParentId2("myId1", "myId2"), "usingEmbeddedId_ParentName");
        em.persist(parent);

        ParentId2 parentId = new ParentId2("myId1", "myId2");
        Parent2 findParent = em.find(Parent2.class, parentId);
        System.out.println("=============== usingEmbeddedId ================");
        System.out.println(findParent.getName());
        System.out.println("=============== usingEmbeddedId ================\n");


    }

    private static void usingIdClass(EntityManager em) {
        Parent1 parent = new Parent1();
        parent.setId1("myId1");
        parent.setId2("myId2");
        parent.setName("usingIdClass_ParentName");
        em.persist(parent);

        ParentId1 parentId = new ParentId1("myId1", "myId2");
        Parent1 findParent = em.find(Parent1.class, parentId);
        System.out.println("================ usingIdClass ===============");
        System.out.println(findParent.getName());
        System.out.println("================ usingIdClass ===============\n");

    }
}
