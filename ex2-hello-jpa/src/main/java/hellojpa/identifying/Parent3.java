package hellojpa.identifying;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//부모 엔티티
@Entity
@Data
public class Parent3 {
    @Id @Column(name = "PARENT_ID")
    private String id;

    private String name;


}

