package hellojpa.nonIdentifying;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @EmbeddedId 사용하기
 */
@Entity
@Data
@NoArgsConstructor
public class Parent2 {
    @EmbeddedId
    private ParentId2 id;

    private String name;

    public Parent2(ParentId2 id, String name) {
        this.id = id;
        this.name = name;
    }
}
