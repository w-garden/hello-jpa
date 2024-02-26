package hellojpa.jointable.ontToOne;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Child6 {

    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "child")
    private Parent6 parent;
}
