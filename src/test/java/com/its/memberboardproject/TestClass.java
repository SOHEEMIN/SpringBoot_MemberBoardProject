package com.its.memberboardproject;

import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.MemberRepository;
import com.its.memberboardproject.service.BoardService;
import com.its.memberboardproject.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberRepository memberRepository;

    public BoardDTO newBoard(int i){
        BoardDTO board = new BoardDTO("제목"+i, "글쓴이"+i, "글내용"+i, i, "게시글파일");
        return board;
    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void newMember(){
        MemberDTO memberDTO = new MemberDTO("email1", "비번", "이름", "핸드폰번호", "회원파일");
        memberRepository.save(MemberEntity.toMember(memberDTO));
    }
}
