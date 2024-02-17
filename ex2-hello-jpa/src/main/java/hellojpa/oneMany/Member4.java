package hellojpa.oneMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 일대다 매핑관계
 */
@Getter
@Setter
@Entity
public class Member4 {
    public Member4(String username) {
        this.username = username;
    }

    public Member4() {
    }

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @ManyToOne //일대다 매핑일때 읽기전용으로 사용하기 위해
    @JoinColumn(name="TEAM_ID",insertable = false, updatable = false)
    private Team4 team;

    private String username;

}
