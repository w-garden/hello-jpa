package hellojpa.identifying.idClass;

import lombok.Data;

import javax.persistence.*;
//자식 엔티티
@Data
@Entity
@IdClass(ChildId3.class)
public class Child3 {

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent3 parent;

    @Id @Column(name = "CHILD_ID")
    private String child;

    private String name;


}
