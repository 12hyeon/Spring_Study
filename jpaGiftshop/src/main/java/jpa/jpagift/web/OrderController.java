package jpa.jpagift.web;

import jpa.jpagift.domain.Order;
import jpa.jpagift.domain.OrderItem;
import jpa.jpagift.domain.GiftBox;
import jpa.jpagift.service.GiftBoxService;
import jpa.jpagift.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class OrderController {

    private final OrderService orderService;
    private final GiftBoxService giftBoxService;

    @PostMapping(value = "/order/cart")
    public String addCart(@RequestParam String itemName,
                          @RequestParam String sender,
                          @RequestParam int count) {
        OrderItem orderItem = orderService.addCart(sender, itemName, count);
        return "장바구니 담기 성공 - "
                + orderItem.getItem().getName() + " " + orderItem.getCount()+"개";
    }

    @PostMapping(value = "/order/new")
    public String order(@RequestParam("sender") String sender,
                        @RequestParam("orderItemId") String orderItemId,
                        @RequestParam("receiver") String receiver) {

        System.out.println(sender);
        System.out.println(orderItemId);
        System.out.println(receiver);

        List<Long> orderItemIds = new ArrayList<>();
        String[] array = orderItemId.split(",");
        for(String a : array) {
            orderItemIds.add(Long.parseLong(a));
        }
        System.out.println("service - order");
        Order order = orderService.order(sender, orderItemIds, receiver);

        return sendOrder(sender, order.getId(), receiver);
    }

    public String sendOrder(String sender, Long orderId, String receiver) {
        Order order = orderService.findId(orderId);
        giftBoxService.sendGift(sender, order, receiver);
        GiftBox giftBox = giftBoxService.findGift(receiver);

        String result = "";
        for(OrderItem orderItem : order.getOrderItems()) {
            result += "- " + orderItem.getItem().getName() + "\n";
        }


        return "[상품 전달 성공]\n" + order.getReceiver() +"에게\n"
                + order.getMember().getEmail()+"가\n" + result;
    }


}