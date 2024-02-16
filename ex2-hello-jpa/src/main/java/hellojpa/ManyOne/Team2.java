package hellojpa.ManyOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team2 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") //Team에서 Member로는 1:다
    private List<Member2> members = new ArrayList<>();



//    public void addMember(Member2 member){
//        member.setTeam(this);
//        members.add(member);
//    }


}
