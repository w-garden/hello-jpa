package hellojpa.identifying.transferNonIdentifying;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Parent5 {

    @Id @GeneratedValue
    @Column(name = "PARNET_ID")
    private Long id;

    private String name;
}
