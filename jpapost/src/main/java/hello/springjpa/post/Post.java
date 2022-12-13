package hello.springjpa.post;

import hello.springjpa.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Setter @Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동 생성
    private Long id;
    private String title;
    private String description;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Post createPost(Member  member, String title, String description) {
        Post post = new Post();

        post.setMember(member);

        post.setDate(new Date());
        post.setTitle(title);
        post.setDescription(description);
        return post;
    }
}
