package jpabook.jpastore.service;


import jpabook.jpastore.domain.Address;
import jpabook.jpastore.domain.Member;
import jpabook.jpastore.domain.Order;
import jpabook.jpastore.domain.OrderStatus;
import jpabook.jpastore.domain.item.Book;
import jpabook.jpastore.domain.item.Item;
import jpabook.jpastore.eception.NotEnoughStockException;
import jpabook.jpastore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member= createMember();
        Item item=creatBook("JPA BOOK", 10000, 15);
        int orderCount =10;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(5, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다");

    }

//    @Test(expected =NotEnoughStockException.class)
    @Test
    public void 상품주문_재고수량초과() {
        //given
        Member member = createMember();
        Item item = creatBook("시골 JPA", 10000, 10);

        int orderCount =11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    public void 주문취소() {
        //given
        Member member = createMember();
        Item item = creatBook("시골 JPA", 10000, 10);
        int orderCount =2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(member.getId());
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL 이다");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가 해야 한다");
    }
    private Member createMember() {

        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강남구", "123-123"));
        em.persist(member);
        return member;
    }

    private Book creatBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }


}