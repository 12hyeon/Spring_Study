package jpa.jpagift.repository;

import jpa.jpagift.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item) {
        if (item.getId() == null) {
            System.out.println("item persist!");
            em.persist(item);
        } else { // item id가 존재하는 경우, DB의 내용 수정 진행으로 인식
            System.out.println("item merge!");
            em.merge(item); //  detached 된 상태 -> persist 상태
        }
        return item.getId();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findByName(String name) {
        return em.createQuery("select i from Item i where i.name = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Item> findByBrand(String brand) {
        return em.createQuery("select i from Item i where i.brand = :brand", Item.class)
                .setParameter("brand", brand)
                .getResultList();
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

    // 포함하는 상품 이름 검색
    public List<Item> findByNameContaining(String word) {
        return em.createQuery("select i from Item i where i.name conatains :word", Item.class)
                .setParameter("word", word)
                .getResultList();
    }

    @Transactional
    Item update(Item item) { //item: 파리미터로 넘어온 준영속 상태의 엔티티
        Item mergeItem = em.merge(item);
        return mergeItem;
    }
}