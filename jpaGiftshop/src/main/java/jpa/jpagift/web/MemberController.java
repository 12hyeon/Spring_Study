package jpa.jpagift.web;

import jpa.jpagift.domain.Member;
import jpa.jpagift.domain.Address;
import jpa.jpagift.service.GiftBoxService;
import jpa.jpagift.service.MemberService;
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
    private final GiftBoxService giftBoxServicey;

    // 로그인
    @PostMapping(value = "/members")
    public String login(@RequestParam("email") String email,
                        @RequestParam("pwd") String pwd) {
        Long result = memberService.login(email, pwd);
        if (result == Long.parseLong("-1")) {
            return "로그인 실패!";
        }
        return "로그인 성공 - id"+result;
    }

    // 회원가입
    @PostMapping(value = "/members/new")
    public String join(@RequestParam("email") String email, @RequestParam("pwd") String pwd,
                         @RequestParam("name") String name, @RequestParam("street") String street,
                         @RequestParam("detail") String detail, @RequestParam("zipcode") String zipcode,
                         @RequestParam("school") String school, @RequestParam("station") String station) {

        Address address = new Address(street, detail, zipcode);
        Member member = new Member();

        member.setEmail(email);
        member.setPwd(pwd);
        member.setName(name);
        member.setAddress(address);
        member.setSchool(school);
        member.setStation(station);

        Long result = memberService.join(member);
        if (result == Long.parseLong("-1")) {
            return "회원가입 실패!";
        }
        return "회원가입 성공 - id"+result;
    }

    // 캐시 추가
    @PostMapping(value = "/members/cache")
    public int showBox(@RequestParam String email, @RequestParam int cache) {
        return memberService.addCache(email, cache);
    }

    // 전체 회원 조회
    @GetMapping(value = "/members/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }



    // 선물함 조회 -> 없는 경우, 에외처리 안함?
    @GetMapping(value = "/members/box")
    public String showBox(@RequestParam String email) {
        return "선물함의 선물 개수 : "+ giftBoxServicey.findCount(email);
    }

    // 브랜드 찾기
    // 회원 정보 수정
    // 선물함의 선물에 대해 주문 시작하기
}