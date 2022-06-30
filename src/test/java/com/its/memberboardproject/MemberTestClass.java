package com.its.memberboardproject;

import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.repository.MemberRepository;
import com.its.memberboardproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberTestClass {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO member = new MemberDTO("이메일"+i, "비밀번호"+i, "이름"+i, "전화번호"+i, "파일"+i);
        return member;
    }
    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    @Rollback(value = false)
    public void saveTest() {
        Long savedId = memberService.saveTest(newMember(1));
        MemberDTO memberDTO = memberService.findById(savedId);
        System.out.println("MemberTestClass.saveTest");
        System.out.println("memberDTO = " + memberDTO);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest(){
        final String memberEmail ="로그인용이메일";
        final String memberPassword = "로그인용비밀번호";
        String memberName = "로그인용 이름";
        String memberMobile = "로그인용 전화번호";
        String memberProfileName = "로그인용 파일";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberMobile, memberProfileName);
        Long saveId = memberService.saveTest(memberDTO);
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        assertThat(loginResult).isNotNull();
    }
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        Long id = memberService.saveTest(newMember(100));
        memberService.deleteById(id);
        assertThat(memberService.findById(id)).isNull();
    }
}
