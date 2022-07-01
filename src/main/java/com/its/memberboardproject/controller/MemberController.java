package com.its.memberboardproject.controller;

import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //회원가입 화면 이동
    @GetMapping("/save")
    public String save() {
        return "/memberPages/saveForm";
    }

    //회원가입 처리
    @PostMapping("/save")
    public String saveForm(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "/memberPages/login";
    }
    //회원목록 조회
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        System.out.println("MemberController.findAll");
        System.out.println("memberDTOList = " + memberDTOList);
        return "/memberPages/list";
    }
    //아이디 중복체크
    @PostMapping("/dup-check")
    public @ResponseBody String duplicatedCheck(@RequestParam String memberEmail){
        String checkResult = memberService.duplicatedCheck(memberEmail);
        return checkResult;
    }
    //로그인 화면 요청
    @GetMapping("/loginForm")
    public String login (){
        return "memberPages/login";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            session.setAttribute("id", loginResult.getId());
            return "index";
        } else {
            return "/memberPages/saveForm";
        }
    }
    //로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
    //관리자 화면 이동
    @GetMapping("/admin")
    public String admin(){
        return "/memberPages/admin";
    }
    //회원 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    //수정 화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        Long id = (Long) session.getAttribute("id");
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("updateMember", memberDTO);
        System.out.println("MemberController.updateForm");
        System.out.println("memberDTO = " + memberDTO);
        return "memberPages/update";
    }
    //수정 처리
    @PutMapping("/{id}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO){
        memberService.update(memberDTO);
        System.out.println("MemberController.updateByAjax");
        System.out.println("memberDTO = " + memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
