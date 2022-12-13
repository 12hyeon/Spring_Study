package jpa.jpagift.repository;

import jpa.jpagift.domain.GiftBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftBoxRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(GiftBox giftBox) {
        System.out.println("id :" +giftBox.getId());
        if (giftBox.getId() == null) {
            em.persist(giftBox);
        } else {
            em.merge(giftBox);
        }
        GiftBox giftBox1 = findBySenderAndReceiver(giftBox.getSender(), giftBox.getReceiver()).get(0);
        return giftBox1.getId();
    }

    public GiftBox findOne(Long id) {
        return em.find(GiftBox.class, id);
    }

    public List<GiftBox> findAll() {
        return em.createQuery("select g from GiftBox g", GiftBox.class)
                .getResultList(); // 결과를 모두 가져오기
    }


    public List<GiftBox> findBySenderAndReceiver(String sender, String receiver) {
        List<GiftBox>  g = em.createQuery("select g from GiftBox g " +
                        "where g.sender = :sender and g.receiver = :receiver", GiftBox.class)
                .setParameter("receiver", receiver)
                .setParameter("sender", sender)
                .getResultList();
        System.out.println(g.size());
        return g;
    }

    public List<GiftBox> findByReceiver(String receiver) {
        List<GiftBox>  g = em.createQuery("select g from GiftBox g where g.receiver = :receiver",
                        GiftBox.class)
                .setParameter("receiver", receiver).
                getResultList();
        return g;
    }

    @Transactional
    GiftBox update(GiftBox giftBox) {
        GiftBox mergeGiftBox = em.merge(giftBox);
        return mergeGiftBox;
    }

    @Transactional // 원래는 public으로 사용x -> update & remove
    public void remove(GiftBox giftBox) {
        em.remove(giftBox);
    }
}