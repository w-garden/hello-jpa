package hellojpa.jointable.ontToOne;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Parent6 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinTable(name = "PARENT_CHILD", //매핑할 조인 테이블 이름
            joinColumns = @JoinColumn(name = "PARENT_ID"), //현재 엔티티를 참조하는 외래 키
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID") //반대방향 엔티티를 참조하는 외래 키
    )
    private Child6 child;


}
