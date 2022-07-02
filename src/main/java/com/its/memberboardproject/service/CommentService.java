package com.its.memberboardproject.service;

import com.its.memberboardproject.dto.BoardDTO;
import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.entity.BoardEntity;
import com.its.memberboardproject.entity.CommentEntity;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.BoardRepository;
import com.its.memberboardproject.repository.CommentRepository;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<CommentDTO> findAll(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
            for (CommentEntity commentEntity : commentEntityList) {
                commentDTOList.add(CommentDTO.toSaveDTO(commentEntity));
            }
        }
        return commentDTOList;
    }

    public void save(CommentDTO commentDTO) {
        System.out.println("CommentService.save");

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter());
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());

        if (optionalBoardEntity.isPresent() && optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity);
            commentRepository.save(commentEntity);
        }
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public CommentDTO findById(Long id) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
        if (optionalCommentEntity.isPresent()) {
            CommentEntity commentEntity = optionalCommentEntity.get();
            return CommentDTO.toSaveDTO(commentEntity);
        } else {
            return null;
        }
    }
}
