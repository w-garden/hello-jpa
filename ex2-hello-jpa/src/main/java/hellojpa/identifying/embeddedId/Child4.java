package hellojpa.identifying.embeddedId;

import javax.persistence.*;

@Entity
public class Child4 {

    @MapsId("parentId") //ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent4 parent;

    @EmbeddedId
    private ChildId4 id;

    private String name;
}
