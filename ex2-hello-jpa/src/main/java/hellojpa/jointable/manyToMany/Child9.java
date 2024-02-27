package hellojpa.jointable.manyToMany;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Child9 {
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;
}
