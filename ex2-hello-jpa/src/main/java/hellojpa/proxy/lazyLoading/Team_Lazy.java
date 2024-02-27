package hellojpa.proxy.lazyLoading;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Team_Lazy {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member_Lazy> members = new ArrayList<>();

    public void addMember(Member_Lazy member){
        member.setTeam(this);
        members.add(member);
    }
}
