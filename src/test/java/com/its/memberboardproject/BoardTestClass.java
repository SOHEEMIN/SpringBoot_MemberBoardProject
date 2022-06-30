package com.its.memberboardproject;

import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.entity.BoardEntity;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.BoardRepository;
import com.its.memberboardproject.repository.MemberRepository;
import com.its.memberboardproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardTestClass {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberService memberService;

    public BoardDTO newBoard(int i) {
        BoardDTO board = new BoardDTO("제목" + i, "글쓴이" + i, "글내용" + i, i, "게시글파일");
        return board;
    }

    public MemberDTO newMember(int i) {
        MemberDTO memberDTO = new MemberDTO("아이디" + i, "비밀번호" + i, "이름" + i, "전화번호" + i, "프로필" + i);
        return memberDTO;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void newMember() {
        MemberDTO memberDTO = new MemberDTO("email1", "비번", "이름", "핸드폰번호", "회원파일");
        memberRepository.save(MemberEntity.toMember(memberDTO));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트")
    public void memberBoardSaveTest() {
        BoardDTO boardDTO = newBoard(1);
        System.out.println("BoardTestClass.memberBoardSaveTest");
        System.out.println("boardDTO = " + boardDTO);
        MemberEntity memberEntity = memberRepository.findByMemberEmail("email1").get();
        BoardEntity boardEntity = BoardEntity.toBoard(boardDTO, memberEntity);
        boardRepository.save(boardEntity);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트 조회")
    public void memberBoardFindByIdTest() {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(1L);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            System.out.println("boardEntity.getId() = " + boardEntity.getId());
            System.out.println("boardEntity.getBoardTitle() = " + boardEntity.getBoardTitle());
            System.out.println("boardEntity.getMemberEntity().getMemberName() = " + boardEntity.getMemberEntity().getMemberName());
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 삭제 테스트")
    public void deleteTest() {
        boardRepository.deleteById(1L);
        memberRepository.deleteById(1L);
    }


}
