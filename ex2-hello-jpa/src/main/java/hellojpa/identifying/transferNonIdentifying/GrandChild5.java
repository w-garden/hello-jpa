package hellojpa.identifying.transferNonIdentifying;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class GrandChild5 {

    @Id @GeneratedValue
    @Column(name = "GRANDCHILD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHILD_ID")
    private Child5 child;

    private String name;
}
