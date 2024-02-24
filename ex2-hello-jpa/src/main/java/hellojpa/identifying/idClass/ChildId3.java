package hellojpa.identifying.idClass;

import java.io.Serializable;
//자식 ID
public class ChildId3 implements Serializable {
    private String parent; //Child4.parent 매핑
    private String child; //Child4.childId 매핑

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildId3)) return false;

        ChildId3 childId3 = (ChildId3) o;

        if (!parent.equals(childId3.parent)) return false;
        return child.equals(childId3.child);
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + child.hashCode();
        return result;
    }
}
