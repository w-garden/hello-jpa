package hellojpa.secondaryTable;

import javax.persistence.*;

@Entity
@Table(name = "BOARD1") //BOARD1 테이블과 매핑
@SecondaryTable(name = "BOARD_DETAIL1", //BOARD_DETAIL1 테이블 추가
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID")) //매핑할 다른 테이블의 기본키 컬럼 속성
public class Board1 {
    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;

    @Column(table = "BOARD_DETAIL1")
    private String content;
}
