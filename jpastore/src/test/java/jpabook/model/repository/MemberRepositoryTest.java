package jpabook.model.repository;

import jpabook.model.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    public void testMember() {
        //given
        Member member = new Member();
        member.setName("memberA");
        //when
        Long saveId = memberRepository.save(member).getId();
//        Member findMember = memberRepository.findOne(saveId);

        //then
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getName()).isEqualTo(member.getName());
//        assertThat(findMember).isEqualTo(member);
    }

}