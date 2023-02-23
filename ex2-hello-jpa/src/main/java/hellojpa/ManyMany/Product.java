package hellojpa.ManyMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
@Setter
public class Product {
    @Id @GeneratedValue
    @Column(name ="PRODUCT_ID")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "products")
    private List<Member> members = new ArrayList<>();
}
