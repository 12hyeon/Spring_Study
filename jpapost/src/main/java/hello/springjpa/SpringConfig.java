package hello.springjpa;

//import hello.hellospring.aop.TimeTraceAop;
import hello.springjpa.post.JpaPostRepository;
import hello.springjpa.post.PostRepository;
import hello.springjpa.post.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    //1) JDBC
    private final DataSource dataSource;

    /*public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    // 2) JPA
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    //private final PostRepository postRepository;

    /*@Autowired
    public SpringConfig(PostRepository postRepository) {
        this.postRepository = postRepository;
    }*/

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        //return new MemoryMemberRepository();
        //return new JdbcPostRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaPostRepository(em);

    }
}
