package hellojpa.ex01;

import javax.persistence.*;

public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private Item item;
    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private Order order;

    private int orderPrice;
    private int count;
}
