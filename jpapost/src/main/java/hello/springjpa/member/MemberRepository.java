package hello.springjpa.member;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@AllArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getMemberId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> getPwd(String id) {
        return em.createQuery("select * from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Member> findById(String id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // 결과를 모두 가져오기
    }

    @Transactional
    public Member update(Member member) {
        Member mergeMember = em.merge(member);
        return mergeMember;
    }

    @Transactional
    public Member updatePwd(Member member, String pwd) {
        member.setPwd(pwd);
        Member mergeMember = em.merge(member);
        return mergeMember;
    }
}