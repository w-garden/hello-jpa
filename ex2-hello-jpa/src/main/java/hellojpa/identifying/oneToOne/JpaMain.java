package hellojpa.identifying.oneToOne;

import hellojpa.nonIdentifying.embeddedId.Parent2;
import hellojpa.nonIdentifying.embeddedId.ParentId2;
import hellojpa.nonIdentifying.idClass.Parent1;
import hellojpa.nonIdentifying.idClass.ParentId1;

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
