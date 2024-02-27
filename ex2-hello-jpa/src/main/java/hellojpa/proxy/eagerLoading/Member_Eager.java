package hellojpa.proxy.eagerLoading;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Member_Eager {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team_Eager team;


    public void setTeam(Team_Eager team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
