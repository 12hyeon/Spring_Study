package jpa.jpagift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private int count;
    private int price;
    private String sender;
    //private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; //주문


    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, String sender, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setPrice(item.getPrice());
        orderItem.setSender(sender);
        orderItem.setItem(item);

        item.removeStock(count); // 주문 들어왔으니 재고 제거
        return orderItem;
    }


    //==비즈니스 로직==//
    /** 주문 취소 */
    public int cancel() {
        return getItem().addStock(count);
    }


    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getPrice() * getCount();
    }
}