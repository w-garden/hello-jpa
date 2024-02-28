package hellojpa.valueType.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//임베디드 타입
@Getter
@Setter
@Entity
public class Member10 {
    @Id @GeneratedValue
    private Long id;

    private String username;

    //근무 기간
    @Embedded
    private Period workPeriod;

    //집 주소
    @Embedded
    private Address homeAddress;

    @Embedded
    private PhoneNumber phoneNumber;
}
