package hellojpa.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Parent {
    @Id @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();
    private String name;

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }
}
