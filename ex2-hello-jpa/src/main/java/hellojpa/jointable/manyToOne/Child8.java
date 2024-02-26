package hellojpa.jointable.manyToOne;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Child8 {

    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinTable(name = "PARENT_CHILD"
            , joinColumns = @JoinColumn(name = "PARENT_ID")
            , inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    private Parent8 parent;
}
