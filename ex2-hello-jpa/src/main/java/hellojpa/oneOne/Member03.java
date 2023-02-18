package hellojpa.oneOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * 1 : 1 관계
 * - 주 테이블에 외래키
 * - 대상 테이블에 외래키
 *
 * 아래 코드는 주 테이블에 외래키 설정
 */
//@Entity
@Getter
@Setter
public class Member03 {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "LOCKER_ID") //외래키가 있는 곳이 연관관계의 주인
    private Locker locker;
    
    @Column(name="USERNAME")
    private String username;

}
