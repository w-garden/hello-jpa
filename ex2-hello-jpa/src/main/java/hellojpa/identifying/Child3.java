package hellojpa.identifying;

import hellojpa.nonIdentifying.Parent2;
import hellojpa.nonIdentifying.ParentId2;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(ChildId3.class)
public class Child3 {
    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent2 parent;

    @Id
    @Column(name = "CHILD_ID")
    private String child;

    private String name;


}
