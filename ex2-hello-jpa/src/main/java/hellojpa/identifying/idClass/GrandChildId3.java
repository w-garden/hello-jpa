package hellojpa.identifying.idClass;

import java.io.Serializable;

//손자ID
public class GrandChildId3 implements Serializable {
    private ChildId3 child; //GrandChild3.child 매핑
    private String id;  //GrandChild3.id 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrandChildId3)) return false;

        GrandChildId3 that = (GrandChildId3) o;

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
