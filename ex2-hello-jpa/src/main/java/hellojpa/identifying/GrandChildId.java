package hellojpa.identifying;

import java.io.Serializable;


public class GrandChildId implements Serializable {
    private String child; //GrandChild.child 매핑
    private String id;  //GrandChild.id 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrandChildId)) return false;

        GrandChildId that = (GrandChildId) o;

        if (!child.equals(that.child)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = child.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
