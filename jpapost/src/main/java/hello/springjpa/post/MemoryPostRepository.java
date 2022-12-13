package hello.springjpa.post;

import java.util.HashMap;
import java.util.Map;

// @Repository
public class MemoryPostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static Long sequence = 0;

    public Long save(Post post) {
        sequence++;
        post.setId(sequence);
        store.put(sequence, post);
        return sequence;
    }

    public Long findId(String title) {
        for(int i=0; i<store.size(); i++) {
            Post post = store.get(i);
            if (post.getTitle() == title) {
                return post.getId();
            }
        }

        return -1;
    }

    public Post find(Long id) {
        return store.get(id);
    }

    public void edit(Post post) {
        Post p = store.get(post.getId());
        p.setTitle(post.getTitle());
        p.setDescription(post.getDescription());
    }

    public void remove(Long id) {
        store.remove(id);
    }

}
