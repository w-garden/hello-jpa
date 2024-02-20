package hellojpa.manyMany;



import hellojpa.manyOne.Member3;
import hellojpa.manyOne.Team3;

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

            save(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void save(EntityManager em) {
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member7 member1 = new Member7();
        member1.setUsername("회원1");
        member1.getProducts().add(productA);
        em.persist(member1);
    }

    private static void oneToMany(EntityManager em) {
        Member3 member = new Member3();

        Team3 team = new Team3();
        team.setName("teamA");

        team.getMembers().add(member);

        em.persist(team);
    }



}
