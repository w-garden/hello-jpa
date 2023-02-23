package hellojpa.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("B") //DTYPE value값 설정
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
