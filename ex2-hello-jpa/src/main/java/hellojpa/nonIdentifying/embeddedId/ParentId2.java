package hellojpa.nonIdentifying.embeddedId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParentId2 implements Serializable {

    @Column(name = "PARENT_Id1")
    private String id1; //Parent1.id1 매핑
    @Column(name = "PARENT_Id2")

    private String id2; //Parent1.id2 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentId2)) return false;

        ParentId2 parentId2 = (ParentId2) o;

        if (!Objects.equals(id1, parentId2.id1)) return false;
        return Objects.equals(id2, parentId2.id2);
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }

    public ParentId2() {
    }

    public ParentId2(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }
}
