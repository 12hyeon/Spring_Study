package jpa.jpagift.service;

import jpa.jpagift.domain.Mail;
import jpa.jpagift.domain.Member;
import jpa.jpagift.domain.OrderItem;
import jpa.jpagift.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final MemberRepository memberRepository;

    public void mailSend(String sender, Mail mail) {
        System.out.println("mailSend");
        try {
            MailHandler mailHandler = new MailHandler(mailSender);

            // 받는 사람
            mailHandler.setTo(mail.getAddress());
            // 보내는 사람
            mailHandler.setFrom(sender);
            // 제목
            mailHandler.setSubject(sender +" send gift!");
            // HTML Layout
            String sentence = "";
            Member member = memberRepository.findByEmail(sender).get(0);
            for(OrderItem item : member.getOrders().get(0).getOrderItems()) {
                sentence += "- " + item.getItem().getName() + " " + item.getCount() + "\n";
            }

            String htmlContent = "[전체 물품]\n" + sentence;
            mailHandler.setText(htmlContent, true);
            System.out.println("mailSender2");
            mailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}