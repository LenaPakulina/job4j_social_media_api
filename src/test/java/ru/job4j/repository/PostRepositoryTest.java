package ru.job4j.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.save(User.builder()
                .name("name")
                .email("email1")
                .password("password")
                .build());
    }

    @AfterEach
    void clearStorages() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @DisplayName("Сохранение")
    @Test
    void whenSavePost() {
        List<User> users = userRepository.findAll();
        Post post = postRepository.save(createPost(users.get(0), "title", "desc"));
        Optional<Post> result = postRepository.findById(post.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getDescription()).isEqualTo(post.getDescription());
    }

    @DisplayName("Удаление")
    @Test
    void whenDeletePost() {
        List<User> users = userRepository.findAll();
        Post post1 = postRepository.save(createPost(users.get(0), "title", "desc"));
        Post post2 = postRepository.save(createPost(users.get(0), "title", "desc"));
        Post post3 = postRepository.save(createPost(users.get(0), "title", "desc"));
        List<Post> allPost = postRepository.findAll();
        assertThat(allPost).isEqualTo(List.of(post1, post2, post3));
    }

    private Post createPost(User user, String title, String description) {
        return Post.builder()
                .user(user)
                .title(title)
                .description(description)
                .build();
    }
}