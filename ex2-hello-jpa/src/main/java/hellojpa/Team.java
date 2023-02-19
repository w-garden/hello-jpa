package hellojpa;

import hellojpa.ManyOne.Member01;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;
    private String name;

//    @OneToMany(mappedBy = "team") //Team에서 Member로는 1:다
//    List<Member02> members = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="TEAM_ID") //추가안하면 JoinTable 전략으로 진행됨
    List<Member> members = new ArrayList<>();



}
