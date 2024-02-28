package hellojpa.manyMany;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member9 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product9 product;

    private int orderAmount;

    private Date orderDate;

}
