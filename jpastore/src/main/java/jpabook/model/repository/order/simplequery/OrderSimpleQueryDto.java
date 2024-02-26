package jpabook.model.repository.order.simplequery;

import jpabook.model.domain.Address;
import jpabook.model.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
            this.orderId = orderId;
            this.name = name; //LAZY 초기화
            this.orderDate =orderDate;
            this.orderStatus = orderStatus;
            this.address = address; //LAZY 초기화
        }
    }

