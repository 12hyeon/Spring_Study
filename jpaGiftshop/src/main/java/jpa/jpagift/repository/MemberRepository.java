package jpa.jpagift.repository;

import jpa.jpagift.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }


    public List<Member> findBySchool(String school) {
        return em.createQuery("select m from Member m where m.school = :school", Member.class)
                .setParameter("school", school)
                .getResultList();
    }

    public List<Member> findByStation(String station) {
        return em.createQuery("select m from Member m where m.station = :station", Member.class)
                .setParameter("station", station)
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

    @Transactional // 캐시 추가
    public int updateCache(Member member, int cache) {
        member.setCache(member.getCache() + cache);
        Member mergeMember = em.merge(member);
        return mergeMember.getCache();
    }
}