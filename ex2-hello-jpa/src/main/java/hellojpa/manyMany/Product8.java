package hellojpa.manyMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product8 {
    @Id
    @Column(name ="PRODUCT_ID")
    private String id;

    private String name;

    public Product8(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
