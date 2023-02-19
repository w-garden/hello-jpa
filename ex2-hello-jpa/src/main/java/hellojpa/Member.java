package hellojpa;

import hellojpa.ManyOne.Team01;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 다대일 매핑관계
 */
@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;


    //연관관계 없이 매핑
//    @Column(name = "TEAM_ID")
//    private Long teamId;

//    @ManyToOne //Member입장에서 Many, Team입장에서 one
//    @JoinColumn(name="TEAM_ID")
//    private Team02 team01;

    @ManyToOne //일대다 매핑일때 읽기전용으로 사용하기 위해
    @JoinColumn(name="TEAM_ID",insertable = false, updatable = false)
    private Team team;


    @Column(name = "USERNAME")
    private String username;

//    public void changeTeam(Team02 team01) { //연관관계 편의 메서드
//        this.team01=team01;
//        team01.getMembers().add(this);
//    }

}
