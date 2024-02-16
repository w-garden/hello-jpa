package hellojpa.manyOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team3 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team") //Team에서 Member로는 1:다
    private List<Member3> members = new ArrayList<>();



//    public void addMember(Member3 member){
//        member.setTeam(this);
//        members.add(member);
//    }


}
