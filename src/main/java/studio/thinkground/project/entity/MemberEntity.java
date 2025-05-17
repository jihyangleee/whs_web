package studio.thinkground.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import studio.thinkground.project.dto.MemberDTO;

@Entity
@Setter
@Getter
@Table(name= "member_table") //db에 이렇게 테이블이 만들어짐
public class MemberEntity {
    @Id //pk 지정
    @GeneratedValue() //auto_increment
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity  = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

}
