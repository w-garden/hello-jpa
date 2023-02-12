package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
//@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq")
@TableGenerator(
        name = "member_seq_generator",
        table = "my_sequences",
        pkColumnValue = "member_seq", allocationSize = 1
)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "member_seq_generator")
    private Long id;
    @Column(name = "name", nullable = false)
    private String username;

    private int age;

    @Enumerated(EnumType.STRING) //Enum 타입 매핑 -> EnumType.ORDINAL 사용금지
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //날짜타입 매핑
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testLocalDate; //JPA가 자동으로 매핑해줌
    private LocalDateTime testLocalDateTime;

    @Lob // CLOB(문자일때), BLOB(그외)
    private String description;


    @Transient //매핑 안함
    private int test;

    public Member() {
    }

}


