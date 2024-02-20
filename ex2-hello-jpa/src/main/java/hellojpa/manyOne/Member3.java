package hellojpa.manyOne;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 다대일 매핑 관계(Member3 : Team)
 */
@Entity
@Getter
@Setter
public class Member3 {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne //Member입장에서 Many, Team입장에서 one
    @JoinColumn(name = "TEAM_ID")
    private Team3 team;

    @Column(name = "USERNAME")
    private String username;

    public void setTeam(Team3 team) { //연관관계 편의 메서드
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }




}
