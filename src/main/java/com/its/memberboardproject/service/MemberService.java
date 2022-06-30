package com.its.memberboardproject.service;

import com.its.memberboardproject.dto.MemberDTO;
import com.its.memberboardproject.entity.MemberEntity;
import com.its.memberboardproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberFile = memberDTO.getMemberProfile();
        String memberProfileName = memberFile.getOriginalFilename();
        memberProfileName = System.currentTimeMillis()+"_"+ memberProfileName;
        String savePath = "D:\\springboot_img\\" + memberProfileName;
        if(!memberFile.isEmpty()){
            memberFile.transferTo(new File(savePath));
        }
        memberDTO.setMemberProfileName(memberProfileName);
        return memberRepository.save(MemberEntity.toMember(memberDTO)).getId();
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity>entityList = memberRepository.findAll();
        List<MemberDTO>dtoList = new ArrayList<>();
        for(MemberEntity memberEntity:entityList){
            dtoList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return dtoList;
    }

    public String duplicatedCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMemberEntity.isEmpty()){
            return "OK";
        } else {
            return "NO";
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(optionalMemberEntity.isPresent()) {
            MemberEntity loginEntity = optionalMemberEntity.get();
            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return memberDTO.toMemberDTO(loginEntity);
            } else {
                return null;
            }
        }else {
            return null;
        }
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public Long saveTest(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMember(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        System.out.println("memberDTO = " + memberDTO);
        return id;
    }
}
