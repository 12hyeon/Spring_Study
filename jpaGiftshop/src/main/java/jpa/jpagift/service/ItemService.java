package jpa.jpagift.service;

import jpa.jpagift.domain.Item;
import jpa.jpagift.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }


    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findName(String itemName) {
        return itemRepository.findByName(itemName);
    }

    public List<Item> findBrand(String brand) {
        return itemRepository.findByBrand(brand);
    }

    public List<Item> findNameConataing(String word) {
        return itemRepository.findByNameContaining(word);
    }

    /**
     * 영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateItem(Long id, String name, int price) {
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
    }
}