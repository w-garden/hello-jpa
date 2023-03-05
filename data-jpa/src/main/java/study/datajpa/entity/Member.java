package study.datajpa.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name="Member.findByUsername",
        query = "select m from Member m where m.username =:username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="team_id")
    private Team team;


    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team!=null){
            changeTeam(team);
        }

    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


}
