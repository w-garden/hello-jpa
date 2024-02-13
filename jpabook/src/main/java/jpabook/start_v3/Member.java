package jpabook.start_v3;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String city;

    private String street;

    private String zipcode;

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public Member() {
    }
}
