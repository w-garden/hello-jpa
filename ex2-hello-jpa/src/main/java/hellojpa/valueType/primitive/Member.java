package hellojpa.valueType.primitive;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//기본 값타입
@Getter
@Setter
public class Member  {
    @Id @GeneratedValue
    private Long id;

    private String username;

    //근무기간
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    //집 주소 표현
    private String city;
    private String street;
    private String zipcode;

}
