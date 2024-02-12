package jpabook.start_v2;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class JpaMain {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("jpabook").createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            logic(em);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
    }

    private static void logic(EntityManager em) {
        Board board = new Board();
        board.setAge(23);
        board.setUsername("Andy");

        em.persist(board);
        System.out.println("board.getId() = " + board.getId());
    }

}
