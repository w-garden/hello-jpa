package hellojpa.valueTypeShare;

import hellojpa.embedded.Address;
import hellojpa.embedded.Period;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member  {
    @Id @GeneratedValue
    @Column(name ="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //기간 Period
    @Embedded
    private Period period;

    //주소
    @Embedded
    private Address homeAddress;
}
