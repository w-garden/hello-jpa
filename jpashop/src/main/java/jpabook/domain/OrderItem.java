package jpabook.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id @GeneratedValue
    @Column(name ="ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="ORDER_ID")
    private  Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;

}
