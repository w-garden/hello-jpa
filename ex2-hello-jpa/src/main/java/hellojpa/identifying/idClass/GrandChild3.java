package hellojpa.identifying.idClass;

import lombok.Data;

import javax.persistence.*;

//손자
@Data
@Entity
@IdClass(GrandChildId3.class)
public class GrandChild3 {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child3 child;

    @Id
    @Column(name = "GRANDCHILD_ID")
    private String id;

    private String name;
}
