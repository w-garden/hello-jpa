package jpabook.jpastore.service;

import jpabook.jpastore.domain.Member;
import jpabook.jpastore.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //giver
        Member member = new Member();
        member.setUsername("user1");

        //when
        Long savedId =memberService.join(member);
        //then
        assertEquals (member,memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() {
        //giver
        Member member1 = new Member();
        member1.setUsername("user1");
        Member member2 = new Member();
        member2.setUsername("user1");

        //when
        memberService.join(member1);
        try{ memberService.join(member2);
        }
        catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외가 발생해야 한다");
    }
}
