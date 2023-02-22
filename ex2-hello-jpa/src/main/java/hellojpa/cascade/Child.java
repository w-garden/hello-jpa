package hellojpa.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name ="PARENT_ID")
    private Parent parent;
    private String name;
}
