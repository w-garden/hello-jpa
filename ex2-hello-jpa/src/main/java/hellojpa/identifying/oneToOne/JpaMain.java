package hellojpa.identifying.oneToOne;


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
            Board board = new Board();
            board.setTitle("샘플 제목1");
            em.persist(board);

            BoardDetail boardDetail = new BoardDetail();
            boardDetail.setContent("내용입니다");
            boardDetail.setBoard(board);
            em.persist(boardDetail);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }


}
