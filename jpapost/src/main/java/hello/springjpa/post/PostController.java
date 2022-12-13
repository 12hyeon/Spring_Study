package hello.springjpa.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseBody
    @PostMapping(value = "/posts")
    public String createPost(@PathVariable String id,
                             @PathVariable String title,
                             @PathVariable String description) {
        
        Long id1 = postService.create(id, title, description);
        return "<create>\n" + "id : " + id1 +"\n";
    }

    @ResponseBody
    @GetMapping(value="/posts")
    public String readPost(Long id) {
        System.out.println("controller id :"+id);

        Post post = postService.read(id);

        return "<read>\n" + "id :" + post.getId() + "\n" +
                "title : "+ post.getTitle() + "\n"+
                "description : " + post.getDescription();
    }

    @ResponseBody
    @PutMapping(value="/posts")
    public String updatePost(@PathVariable String id,
                             @PathVariable String title,
                             @PathVariable String description) {
        Post post = postService.update(id, title, description);
        return "<update>\n" + "id :" + post.getId() + "\n" +
                "title : "+ post.getTitle() + "\n"+
                "description : " + post.getDescription();
    }

    @ResponseBody
    @DeleteMapping(value="/posts")
    public String deletePost(Long id) {
        postService.delete(id);

        return "<delete> title : "+ id;
    }
}
