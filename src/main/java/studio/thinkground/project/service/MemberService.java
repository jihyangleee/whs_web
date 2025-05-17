package studio.thinkground.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import studio.thinkground.project.dto.MemberDTO;
import studio.thinkground.project.entity.MemberEntity;
import studio.thinkground.project.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    // repository 메소드를 사용하기 위해서 객체를 불러온다 -> 그 객체의 메소드 사용 가능
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // controller에 있는 save메소드 실행
        // repository의 save 메서드 호출 (조건 : entity의 형태만 저장이 가능)
        // 1. dto - > entity 변환(MemberEntity에서)
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); // jpa가 제공해주는 것
        // service에서는 repository 객체를 불러와야 하고 controller에서는 service 객체를 불러와 그 객체의 메소드를 사용해서 데이터를 넘겨줘야 한다.

    }

    public MemberDTO findByEmail(String email) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(email);
        if (optionalMember.isPresent()) {
            MemberEntity memberEntity = optionalMember.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        }
        else{
            return null;
        }
    }
}
