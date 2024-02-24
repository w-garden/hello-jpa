package hellojpa.identifying.transferNonIdentifying;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Child5 {
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent5 parent;

    private String name;
}
