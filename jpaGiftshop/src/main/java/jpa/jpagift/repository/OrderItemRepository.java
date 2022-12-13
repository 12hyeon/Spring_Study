package jpa.jpagift.repository;

import jpa.jpagift.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입
public class OrderItemRepository {

    private final EntityManager em;

    public Long save(OrderItem orderItem) {
        em.persist(orderItem);
        return orderItem.getId();
    }

    public OrderItem findOne(Long id) {
        return em.find(OrderItem.class, id);
    }

    public List<OrderItem> findBySender(String sender) {
        return em.createQuery("select i from OrderItem i where i.sender = :sender", OrderItem.class)
                .setParameter("sender", sender)
                .getResultList();
    }

    // ? DB에 orderItem에서 item가 어떻게 저장되는지

    public List<OrderItem> findAll() {
        return em.createQuery("select i from OrderItem i",OrderItem.class)
                .getResultList();
    }

    @Transactional
    OrderItem update(OrderItem orderItem) { //orderItem: 파리미터로 넘어온 준영속 상태의 엔티티
        OrderItem mergeOrderItem = em.merge(orderItem);
        return mergeOrderItem;
    }
}