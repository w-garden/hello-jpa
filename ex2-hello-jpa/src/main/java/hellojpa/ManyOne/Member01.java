package hellojpa.ManyOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 다대일 매핑관계
 */
//@Entity
@Getter
@Setter
public class Member01 {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne //Member입장에서 Many, Team입장에서 one
    @JoinColumn(name="TEAM_ID")
    private Team01 team;

    @Column(name = "USERNAME")
    private String username;

    public void changeTeam(Team01 team) { //연관관계 편의 메서드
        this.team=team;
        team.getMembers().add(this);
    }


}
