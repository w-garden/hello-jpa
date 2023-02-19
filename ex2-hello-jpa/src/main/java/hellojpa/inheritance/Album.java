package hellojpa.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")  //DTYPE value값 설정
public class Album extends Item{
    private String artist;
}
