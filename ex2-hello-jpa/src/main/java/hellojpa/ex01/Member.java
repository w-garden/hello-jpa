package hellojpa.ex01;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Member {
    @Id @GeneratedValue
    @Column(name= "MEMBER_ID")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
