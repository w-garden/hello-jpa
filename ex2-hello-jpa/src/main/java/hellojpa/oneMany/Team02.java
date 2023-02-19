package hellojpa.oneMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Team02 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name="TEAM_ID") //추가안하면 JoinTable 전략으로 진행됨
    List<Member02> members = new ArrayList<>();

}
