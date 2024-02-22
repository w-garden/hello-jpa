package hellojpa.nonIdentifying;

import java.io.Serializable;
import java.util.Objects;


public class ParentId1 implements Serializable {
    private String id1; //Parent1.id1 매핑
    private String id2; //Parent1.id2 매핑

    public ParentId1() {
    }

    public ParentId1(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentId1)) return false;

        ParentId1 parentId1 = (ParentId1) o;

        if (!Objects.equals(id1, parentId1.id1)) return false;
        return Objects.equals(id2, parentId1.id2);
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }
}
