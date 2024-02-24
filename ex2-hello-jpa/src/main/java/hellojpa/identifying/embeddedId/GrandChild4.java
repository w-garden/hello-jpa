package hellojpa.identifying.embeddedId;

import javax.persistence.*;

//손자
@Entity
public class GrandChild4 {

    @EmbeddedId
    private GrandChildId4 id;

    @MapsId("childId") //GrandChildId3.childId 매핑
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child4 child;

    private String name;
}
