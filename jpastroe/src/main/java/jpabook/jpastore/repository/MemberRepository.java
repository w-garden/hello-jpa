package jpabook.jpastore.repository;

import com.querydsl.jpa.impl.JPAUtil;
import jpabook.jpastore.domain.Member;
import jpabook.jpastore.domain.Order;
import jpabook.jpastore.domain.QMember;
import jpabook.jpastore.domain.QOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByName(String name);



}
