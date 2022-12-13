package hello.springjpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hello.springjpa.member.Secret.JWT_SECRET_KEY;
import static org.apache.tomcat.util.net.openssl.ciphers.Encryption.AES128;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 로그인
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        Member member = userDao.getUserInfo(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(member.getPwd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postLoginReq.getUserPassword().equals(password)){
            int id = userDao.getUserInfo(postLoginReq).getUserId();
            String jwt = jwtService.createJwt(id);
            return new PostLoginRes(id,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
    /*public Member login(String id, String pwd) throws BaseException {
        Member member = memberRepository.getPwd(id).get(0);
        String pwd1;
        try {
            pwd1 = new AES128(Secret.USER_INFO_PASSWORD_KEY).descrypt(pwd);
        }
        catch (Exception e) {
            throw new BaseException(JWT_SECRET_KEY);
        }

        if(pwd.equals(pwd1)) {
            Long memberId = memberRepository.getPwd(id).get(0).getMemberId();
            String jwt = jwtService.createJwt(memberId);
            return ;
        }
        else {
            throw new BaseException(FAILED_TO_LOGIN);
        }*/

        /*if (member.getPwd().equals(pwd) && member.getStatus() == 1) {
            return member.getId();
        }
        return Long.parseLong("-1"); // 로그인 실패
         */
    }

    // 회원가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        // 비활성 유저 처리
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

    // 변경
    @Transactional
    public int addCache(String email, int cache) {
        Member member = memberRepository.findByEmail(email).get(0);
        return 1;
    }

}