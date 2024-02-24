package hellojpa.identifying.embeddedId;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//부모
@Data
@Entity
public class Parent4 {
    @Id @Column(name = "PARENT_ID")
    private String id;

    private String name;
}
