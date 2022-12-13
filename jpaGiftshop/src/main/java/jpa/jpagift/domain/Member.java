package jpa.jpagift.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor
//@Builder
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String pwd;
    private String name;
    @ColumnDefault("0")
    private int cache;
    private String school;
    private String station;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    //@JoinColumn(name = "giftBox_id")
    private GiftBox giftBox; // 선물함

    public Member(String email, String pwd, String name, String school,
                  String station, Address address) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.cache = 0;
        this.school = school;
        this.station = station;
        this.address = address;
        this.giftBox = giftBox;
        //createGiftBox();
    }

/*    private void createGiftBox(Member member) {
        GiftBox giftBox = new GiftBox();
        giftBox.setSender(this.member);
        giftBox.setReceiver(receiver.getEmail());
        giftBox.setMember(receiver);

        return giftBox;
    }*/
}
