package com.its.memberboardproject.controller;

import com.its.memberboardproject.dto.CommentDTO;
import com.its.memberboardproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    //댓글 저장
    @PostMapping("/save")
    public String save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("CommentController.save");
        commentService.save(commentDTO);
        return "redirect:/board/" + commentDTO.getBoardId();
    }

    //댓글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        commentService.delete(id);
        return "redirect:/board/";
    }
}
