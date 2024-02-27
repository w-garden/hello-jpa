package hellojpa.jointable.manyToMany;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Parent9 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    private List<Child9> child = new ArrayList<>();
}
