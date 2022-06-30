package com.its.memberboardproject.repository;

import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentDTO> findAll(Long boardId);
}
