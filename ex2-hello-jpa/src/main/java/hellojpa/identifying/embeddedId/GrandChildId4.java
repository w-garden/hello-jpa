package hellojpa.identifying.embeddedId;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//손자ID
@Embeddable
public class GrandChildId4 implements Serializable {
    private ChildId4 childId; //@MapsId("childId")로 매핑

    @Column(name = "GRANDCHILD_ID")
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrandChildId4)) return false;

        GrandChildId4 that = (GrandChildId4) o;

        if (!childId.equals(that.childId)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = childId.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
