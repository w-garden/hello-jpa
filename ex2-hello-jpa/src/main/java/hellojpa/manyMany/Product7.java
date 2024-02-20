package hellojpa.manyMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product7 {
    @Id
    @Column(name ="PRODUCT_ID")
    private String id;

    private String name;

    @ManyToMany(mappedBy = "products")
    private List<Member7> members = new ArrayList<>();

    public void addMember(Member7 member7) {
        members.add(member7);
        member7.getProducts().add(this);
    }

}
