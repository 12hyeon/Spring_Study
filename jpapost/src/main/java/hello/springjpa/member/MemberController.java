package hello.springjpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /*@PostMapping(value = "/members")
    public String login(@RequestParam("id") String id,
                        @RequestParam("pwd") String pwd) {
        Long result = memberService.login(id, pwd);

        if (result == Long.parseLong("-1")) {
            return "로그인 실패!";
        }
        return "로그인 성공 - id"+result;
    }
     */

    // 회원가입
    @PostMapping(value = "/members/new")
    public String join(@RequestParam("id") String id,
                       @RequestParam("pwd") String pwd,
                       @RequestParam("name") String name) {

        Member member = new Member();

        member.setId(id);
        member.setPwd(pwd);
        member.setName(name);
        member.setStatus(1);

        Long result = memberService.join(member);
        if (result == Long.parseLong("-1")) {
            return "회원가입 실패!";
        }
        return "회원가입 성공 - id"+result;
    }
}