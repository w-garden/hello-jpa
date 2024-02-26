package hellojpa.noMapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member1 {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    //연관관계 없이 매핑
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "USERNAME")
    private String username;


    public Member1(String username) {
        this.username = username;
    }
}
