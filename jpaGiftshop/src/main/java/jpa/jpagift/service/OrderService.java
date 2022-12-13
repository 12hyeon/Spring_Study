package jpa.jpagift.service;

import jpa.jpagift.domain.*;
import jpa.jpagift.repository.*;
import jpa.jpagift.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final GiftBoxRepository giftBoxRepository;

    // 장바구니 담기
    @Transactional
    public OrderItem addCart(String sender, String itemName, int count) {
        Item item = itemRepository.findByName(itemName).get(0);

        OrderItem orderItem = OrderItem.createOrderItem(item, sender, count);
        /*OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setPrice(item.getPrice());
        orderItem.setCount(count);
        orderItem.setSender(sender);*/

        Long id = orderItemRepository.save(orderItem);
        OrderItem orderItem1 = orderItemRepository.findOne(id);
        return orderItem;
    }

    /** 주문 */
    @Transactional
    public Order order(String sender, List<Long> ids , String receiver) {
        // 엔티티 생성
        Member s_member = memberRepository.findByEmail(sender).get(0);
        Member r_member = memberRepository.findByEmail(receiver).get(0);

        List<OrderItem> orderItems = new ArrayList<>();
        for(Long id: ids) {
            orderItems.add(orderItemRepository.findOne(id));
        }

        Order order = Order.createOrder(s_member, orderItems, receiver);
        orderRepository.save(order);

        GiftBox giftBox = GiftBox.createGiftBox(sender, order, r_member);
        System.out.println("order id : "+order.getId());
        giftBoxRepository.save(giftBox);
        order.setGiftBox(r_member.getGiftBox());

        Long id = orderRepository.save(order);

        return order;
    }

    public Order findId(Long id) {
        return orderRepository.findOne(id);
    }

    public List<Order> findReceiver(String receiver) {
        return orderRepository.findByReceiver(receiver);
    }


    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        giftBoxRepository.remove(order.getGiftBox());
    }
}
