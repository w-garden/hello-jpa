package hellojpa.identifying;

import lombok.Data;

import javax.persistence.*;

//손자
@Data
@Entity
@IdClass(GrandChildId.class)
public class GrandChild {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID")
            , @JoinColumn(name = "CHILD_ID")
    })
    private Child3 child;

    @Id
    @Column(name = "GRANDCHILD_ID")
    private String id;

    private String name;
}
