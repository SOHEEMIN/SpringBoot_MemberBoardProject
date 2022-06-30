package com.its.memberboardproject.dto;

import com.its.memberboardproject.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private MultipartFile memberProfile;
    private String memberProfileName;


    public MemberDTO(String memberEmail, String memberPassword, String memberName, String memberMobile, String memberProfileName) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberMobile = memberMobile;
        this.memberProfile = memberProfile;
        this.memberProfileName = memberProfileName;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberProfileName(memberEntity.getMemberProfileName());
        return memberDTO;
    }


}
