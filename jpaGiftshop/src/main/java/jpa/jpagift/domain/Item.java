package jpa.jpagift.domain;

import jpa.jpagift.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private String brand;
    private int stockQuantity; //재고

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();


    // 연관 메서드
    public int addStock(int quantity) {
        this.stockQuantity += quantity;
        return stockQuantity;
    }

    public int removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
        return stockQuantity;
    }

}