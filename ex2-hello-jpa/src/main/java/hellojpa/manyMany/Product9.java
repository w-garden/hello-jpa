package hellojpa.manyMany;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Product9 {
    @Id
    @Column(name = "PRODUCT_ID")
    String id;

    String name;

    public Product9(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
