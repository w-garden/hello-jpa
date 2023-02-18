package hellojpa.ex01;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
