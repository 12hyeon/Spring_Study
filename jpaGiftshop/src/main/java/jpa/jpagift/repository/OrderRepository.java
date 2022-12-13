package jpa.jpagift.repository;

import jpa.jpagift.domain.Order;
import jpa.jpagift.domain.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public List<Order> findByDate(String date) {
        return em.createQuery("select o from Order o where o.date = :date", Order.class)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Order> findByReceiver(String receiver) {
        return em.createQuery("select o from Order o where o.receiver = :receiver", Order.class)
                .setParameter("receiver", receiver)
                .getResultList();
    }

    @Transactional
    Order update(Order order) {
        Order mergeOrder = em.merge(order);
        return mergeOrder;
    }

    @Transactional
    public Order update(Order order, OrderStatus status) {
        order.setStatus(status);
        Order mergeOrder = em.merge(order);
        return mergeOrder;
    }
}