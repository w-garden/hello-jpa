package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;


    //연관관계 없이 매핑
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne //Member입장에서 Many, Team입장에서 one
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Column(name = "USERNAME")
    private String username;

//    public void changeTeam(Team team) { //연관관계 편의 메서드
//        this.team=team;
//        team.getMembers().add(this);
//    }

  }
