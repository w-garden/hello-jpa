package hellojpa.oneOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 1 : 1 관계
 */
//@Entity
@Getter
@Setter
public class Locker {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker") //읽기 전용이됨
    private Member03 member;

}
