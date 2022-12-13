package jpa.jpagift.service;

import com.sun.mail.util.logging.MailHandler;
import jpa.jpagift.domain.*;
import jpa.jpagift.repository.GiftBoxRepository;
import jpa.jpagift.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Properties;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class GiftBoxService {

    private final MemberRepository memberRepository;
    private final GiftBoxRepository giftBoxRepository;
    private final MailService mailService;

    // 선물 찾기
    public GiftBox findGift(String email) {
        Member member = memberRepository.findByEmail(email).get(0);
        return giftBoxRepository.findOne(member.getId());
    }

    // 받은 선물 개수 찾기
    public int findCount(String email) {
        Member member = memberRepository.findByEmail(email).get(0);
        return giftBoxRepository.findByReceiver(email).size();
    }

    private JavaMailSender mailSender;
    // 선물 보내기
    public int sendGift(String sender, Order order, String receiver) {
        System.out.println("sendGift");
        //List<GiftBox> giftBoxes = giftBoxRepository.findByReceiver(sender, receiver);
        // null이면 처리할 수 있는 루틴 필요?

        Member r_member = memberRepository.findByEmail(receiver).get(0);
        Member s_member = memberRepository.findByEmail(sender).get(0);
        GiftBox giftBox;

        giftBox = GiftBox.createGiftBox(sender, order, r_member);
        /*if (giftBoxes.size() == 0) {
            giftBox = GiftBox.createGiftBox(sender, order, r_member);
        }
        else {
            r_member.getGiftBox().addOrder(order);
            giftBox = r_member.getGiftBox();
        }*/
        giftBoxRepository.save(giftBox);

        Mail mail = new Mail();
        mail.setAddress(receiver);
        System.out.println("- mailSend");
        mailService.mailSend(sender, mail);

        return giftBox.getOrders().size();
    }
}