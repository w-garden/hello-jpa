package hellojpa.oneMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 일대다 매핑 관계(Team : Member)
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Team4 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name="TEAM_ID") //추가안하면 JoinTable 전략으로 진행됨
    List<Member4> members = new ArrayList<>();

    public Team4(String name) {
        this.name = name;
    }
}
