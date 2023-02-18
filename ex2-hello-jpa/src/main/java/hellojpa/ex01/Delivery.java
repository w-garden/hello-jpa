package hellojpa.ex01;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name ="DELIVERY_ID")
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveryStatus status;
    @OneToOne(mappedBy = "delivery")
    private Order order;
}
