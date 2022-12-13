package hello.springjpa.post;

import hello.springjpa.member.Member;
import hello.springjpa.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

//@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    // 글 작성
   public Long create(String id, String title, String description) {
       Member member = memberRepository.findById(id).get(0);
       Post post = Post.createPost(member, title, description);
       Long id1 = postRepository.save(post);
       return id1;
   }

    // 글 읽기
    public Post read(Long id) {
//        Long id = postRepository.findId(title);
        System.out.println("serviced id :"+id);
        return postRepository.find(id);
    }

/*    // 글 수정
    public void update(String id, String title, String description) {
        Member member = memberRepository.findById(id).get(0);
        Long postId =  postRepository.findId(title);

        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        Post post1 = postRepository.edit(postId, );
        return post1;
    }*/

    // 글 삭제
    public void delete(Long id) {
//        Long id = postRepository.findId(title);
        postRepository.remove(id);
    }
}
