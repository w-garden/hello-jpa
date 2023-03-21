package jpabook.jpastroe.domain.service;


import jpabook.jpastroe.domain.Address;
import jpabook.jpastroe.domain.Member;
import jpabook.jpastroe.domain.item.Book;
import jpabook.jpastroe.repository.OrderRepository;
import jpabook.jpastroe.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member= createMember();
        //when
        //then
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Member creatBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return  book;
    }


}