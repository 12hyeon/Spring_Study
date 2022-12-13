package jpa.jpagift.service;

import jpa.jpagift.domain.Member;
import jpa.jpagift.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 로그인
    public Long login(String email, String pwd) {
        Member member = memberRepository.findByEmail(email).get(0);
        if (member.getPwd().equals(pwd)) {
            return member.getId();
        }
        return Long.parseLong("-1"); // 로그인 실패
    }

    // 회원가입
    @Transactional
    public Long join(Member member) {
        //validateDuplicateMember(member); //중복 회원 검증 제외?
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 찾기
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 캐시 충전
    @Transactional
    public int addCache(String email, int cache) {
        Member member = memberRepository.findByEmail(email).get(0);
        return memberRepository.updateCache(member, cache);
    }

    // 학교 주위 브랜드 조회 -> brand entity 생성?

}