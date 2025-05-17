package studio.thinkground.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import studio.thinkground.project.entity.MemberEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString

public  class MemberDTO { //개체 간의 데이터를 전달하는 객체 /controller로부터 받아온 값들을 service에 넘기고 service에서 repository -> database
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        return memberDTO;
    }


}

