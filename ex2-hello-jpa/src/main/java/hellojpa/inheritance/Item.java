package hellojpa.inheritance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.JOINED) //조인 테이블 전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //단일 테이블 전략 -> 성능이 제일 좋음, DTYPE 필수로 자동 생성
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //구현 클래스 마다 테이블 전략, DiscriminatorColumn 사용 안해도됨
@DiscriminatorColumn(name="DTYPE") //DTYPE 컬럼 생성(default)
public abstract class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
