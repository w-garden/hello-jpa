package hellojpa.identifying.oneToOne;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BoardDetail {

    @Id
    private Long boardId;

    @MapsId //BoardDetail.boardId 매핑
    @OneToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String content;
}
