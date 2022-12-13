package hello.springjpa.post;

public interface PostRepository {
    Long save(Post post);
    Long findId(String title);
    Post find(Long id);
    void edit(Post post);
    void remove(Long id);
}