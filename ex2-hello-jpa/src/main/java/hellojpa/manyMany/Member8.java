package hellojpa.manyMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Member8 {

    @Id
    @Column(name="MEMBER_ID")
    private String id;

    private String username;

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> MemberProducts;

    public Member8(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
