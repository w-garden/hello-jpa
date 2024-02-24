package hellojpa.identifying.embeddedId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//자식 ID
@Embeddable
public class ChildId4 implements Serializable {

    private String parentId; //@MapsId("parentId")

    @Column(name = "CHILD_ID")
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildId4)) return false;

        ChildId4 childId4 = (ChildId4) o;

        if (!parentId.equals(childId4.parentId)) return false;
        return id.equals(childId4.id);
    }

    @Override
    public int hashCode() {
        int result = parentId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
