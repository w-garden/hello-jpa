package hellojpa.jointable.manyToOne;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Parent8 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;


    @OneToMany(mappedBy = "parent")
    private List<Child8> child = new ArrayList<>();
}
