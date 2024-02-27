package hellojpa.proxy.lazyLoading;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Member_Lazy {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team_Lazy team;

    public void setTeam(Team_Lazy team) {
        if (this.team != null) {
            team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }


}
