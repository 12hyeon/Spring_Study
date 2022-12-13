package jpa.jpagift.web;

import jpa.jpagift.domain.Item;
import jpa.jpagift.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class ItemController {

    private final ItemService itemService;

    // item 만들기

    @GetMapping(value = "/items")
    public String findAll() {
        List<Item> items = itemService.findItems();
        String sentence = "";
        for(Item item : items) {
            sentence += "- " + item.getName() + "\n";
        }
        return "[전체 물품]\n" + sentence;
    }

    @GetMapping(value = "/items/brand")
    public String findBrand(@RequestParam String brand) {
        List<Item> items = itemService.findBrand(brand);
        String sentence = "";
        for(Item item : items) {
            sentence += "- " + item.getName() + "\n";
        }
        return "[" + brand + " 브랜드 물품]\n" + sentence;
    }

    @GetMapping(value = "/items/word")
    public String findNameContaining(@RequestParam String word) {
        List<Item> items = itemService.findNameConataing(word);
        String sentence = "";
        for(Item item : items) {
            sentence += "- " + item.getName() + "\n";
        }
        return "['" + word + "' 물품]\n" + sentence;
    }
}