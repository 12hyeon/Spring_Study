package jpa.jpagift.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class GiftBox {
    @Id @GeneratedValue
    @Column(name = "gift_box_id")
    private Long id;

    private String sender;
    private String receiver;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // giftBox <- member에게 넣어주는 구조

    @OneToMany(mappedBy = "giftBox")
    private List<Order> orders = new ArrayList<>();


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.setGiftBox(this);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setGiftBox(this);
    }


    //==생성 메서드==//
    public static GiftBox createGiftBox(String sender, Order order, Member receiver) {
        GiftBox giftBox = new GiftBox();
        giftBox.setSender(sender);
        giftBox.addOrder(order);
        giftBox.setReceiver(receiver.getEmail());
        giftBox.setMember(receiver);

        return giftBox;
    }
}
