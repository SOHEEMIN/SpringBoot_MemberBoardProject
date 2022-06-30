package com.its.memberboardproject.service;

import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.entity.BoardEntity;
import com.its.memberboardproject.entity.CommentEntity;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.BoardRepository;
import com.its.memberboardproject.repository.CommentRepository;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());

        if (optionalMemberEntity.isPresent() && optionalBoardEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = optionalBoardEntity.get();
            Long savedId = commentRepository.save(CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity)).getId();
            return savedId;
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        return commentRepository.findAll(boardId);
    }
}
