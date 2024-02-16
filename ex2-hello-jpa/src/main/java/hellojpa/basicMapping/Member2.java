package hellojpa.basicMapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 다대일 매핑관계
 */
@Entity
@Getter
@Setter
public class Member2 {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne //Member입장에서 Many, Team입장에서 one
    @JoinColumn(name = "TEAM_ID")
    private Team2 team;

    @Column(name = "USERNAME")
    private String username;

    public void setTeam(Team2 team) { //연관관계 편의 메서드
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    public void changeTeam(Team2 team) { //연관관계 편의 메서드
        this.team = team;
        team.getMembers().add(this);
    }


}
