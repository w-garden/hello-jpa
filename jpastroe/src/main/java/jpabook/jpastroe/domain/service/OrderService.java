package jpabook.jpastroe.domain.service;

import jpabook.jpastroe.domain.repository.ItemRepository;
import jpabook.jpastroe.repository.MemberRepository;
import jpabook.jpastroe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
}
