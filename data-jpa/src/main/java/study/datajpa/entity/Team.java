package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of ={"id", "name"})
public class Team extends JpaBaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
