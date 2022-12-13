package hello.springjpa.member;

import hello.springjpa.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;
    private String id;
    private String pwd;
    private String name;
    private int status;
    private Date date;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public member Member(long memberId, String name, String pwd, int status, Date date, String id) {
        Member member = new Member;
        member.setName(name);
        member.setPwd(pwd);
        member.setStatus(status);
        member.setId(id);
        return member;
    }
}
