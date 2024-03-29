package hellojpa.oneOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
/**
 * 일대일 매핑 관계(주 테이블:대상 테이블, Member5 : Locker5)
 */
@Entity
@Getter
@Setter
public class Member5 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String username;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID") //외래키가 있는 곳이 연관관계의 주인
    private Locker5 locker;


}
