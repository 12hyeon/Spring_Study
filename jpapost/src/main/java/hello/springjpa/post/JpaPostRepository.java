package hello.springjpa.post;

import hello.springjpa.member.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaPostRepository implements PostRepository {
    @PersistenceContext
    private final EntityManager em;

    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Post post) {
        em.persist(post);
        Post result = find(post.getId());
        return result.getId();
    }

    public List<Post> findId(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }

    public Post find(Long id) {
        System.out.println("repository id :"+id);
        Post p1 = em.find(Post.class, id);
        System.out.println("em id :"+p1.getId());
        return p1;
    }

    public void edit(Post post) {
        Post p2 = em.find(Post.class, post.getId());
        p2.setTitle(post.getTitle());
        p2.setDescription(post.getDescription());
        em.persist(p2);

    }

    public void remove(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }
}
