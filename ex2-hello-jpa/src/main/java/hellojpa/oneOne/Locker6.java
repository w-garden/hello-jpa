package hellojpa.oneOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 일대일 매핑 관계(대상 테이블 : 주 테이블, Locker5 : Member5)
 */
@Entity
@Getter
@Setter
public class Locker6 {
    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member5 member;

}
