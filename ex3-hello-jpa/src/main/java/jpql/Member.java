package jpql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQuery(
        name="Member.findByUsername",
        query = "select m from Member m where m.username=:username"
)
@Getter
@Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;


    public void changeTeam(Team team) {
        this.team=team;
        team.getMembers().add(this);
    }
    @Override
    public String toString() {
        return  "id : " + id +
                ", username : '" + username + '\'' +
                ", age : " + age
                ;
    }
}
