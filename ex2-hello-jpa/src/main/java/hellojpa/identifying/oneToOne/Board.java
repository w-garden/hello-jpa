package hellojpa.identifying.oneToOne;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Board {

    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;
}
