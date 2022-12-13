package jpa.jpagift.domain;

import jpa.jpagift.domain.status.OrderStatus;
import lombok.Getter;import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Date date;
    private String receiver;
    //private String memberId;
    //private String orderItemId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_box_id")
    private GiftBox giftBox; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //?
    private List<OrderItem> orderItems = new ArrayList<>(); // List만 new까지 사용


    //==연관관계 메서드==// <- 생성 메서드를 위해
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    //==생성 메서드==//
    public static Order createOrder(Member member, List<OrderItem> orderItems, String receiver) {
        Order order = new Order();

        order.setMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setReceiver(receiver);
        order.setStatus(OrderStatus.ORDER);
        order.setDate(new Date());

        return order;
    }


    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        if (getStatus() == OrderStatus.COMP) {
            throw new IllegalStateException("이미 배송 신청된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
        setDate(new Date()); // 취소한 시간 저장
    }

    //==조회 로직==//
    /** 전체 주문 가격 조회 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}