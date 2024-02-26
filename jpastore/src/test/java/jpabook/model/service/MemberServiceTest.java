package jpabook.model.service;

import jpabook.model.domain.Member;
import jpabook.model.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //giver
        Member member = new Member();
        member.setName("user1");

        //when
        Long savedId =memberService.join(member);
        //then
        assertEquals (member,memberRepository.findById(savedId).get());
    }

    @Test
    public void 중복_회원_예외() {
        //giver
        Member member1 = new Member();
        member1.setName("user1");
        Member member2 = new Member();
        member2.setName("user1");

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
