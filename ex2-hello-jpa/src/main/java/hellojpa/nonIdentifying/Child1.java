package hellojpa.nonIdentifying;

import javax.persistence.*;

@Entity
public class Child1 {
    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1"), //name과 referencedColumnName 의 속성이 같으면 referencedColumnName 을 생략해도됨
            @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    })
    private Parent1 parent;
}
