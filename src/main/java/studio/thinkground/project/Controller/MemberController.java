package studio.thinkground.project.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import studio.thinkground.project.dto.MemberDTO;
import studio.thinkground.project.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입 -> MemberController 객체는 밑의 객체의 메소드를 사용할 수 있다.
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/member/save2") //회원가입 페이지만 띄워주는 창만 가지고 있는 것
    public String saveForm(){
        return "save2";
    } //회원가입 저장 버튼을 눌렀을 때 405라는 에러가 뜬다. 주소가 있지만 처리 방식이 다를때
    // 404는 주소가 없을 때
    @PostMapping("/member/save2") //form에 다가 적은 것을 controller 로 받아준다.
//    @RequestParam ("memberEmail")String memberEmail, @RequestParam ("memberPassword") String memberPassword, @RequestParam ("memberName")String memberName을
    //MemberDTO memberDTO라고 적는다.
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.save");
        System.out.println("memberDTO  : "+memberDTO);
        memberService.save(memberDTO);// controller에서 service로 데이터를 넘겨준다. 그리고 이것을 레포지터리에 넣는다.
        return "index2"; //null 리턴일때는 에러를 호출한다.
    }

    @GetMapping("/member/login")
    public String login(){
        return "login";
    }

    @PostMapping("/member/login")
    public String compare(@RequestParam String email, @RequestParam String password){
        MemberDTO memberDTO = memberService.findByEmail(email);
        if (email.equals(memberDTO.getMemberEmail()) && password.equals(memberDTO.getMemberPassword())){
            return "redirect:/board/index";
        }
        else{
            return "login";
        }

    }

}
