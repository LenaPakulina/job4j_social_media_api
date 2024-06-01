package ru.job4j.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.time.LocalDateTime;
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
        userRepository.save(User.builder()
                .name("name")
                .email("email2")
                .password("password")
                .build());
    }

    @AfterEach
    void clearStorages() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("Сохранение")
    @Test
    void whenSavePost() {
        List<User> users = userRepository.findAll();
        Post post = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        Optional<Post> result = postRepository.findById(post.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getDescription()).isEqualTo(post.getDescription());
    }

    @DisplayName("Удаление")
    @Test
    void whenDeletePost() {
        List<User> users = userRepository.findAll();
        Post post1 = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        Post post2 = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        Post post3 = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        List<Post> allPost = postRepository.findAll();
        assertThat(allPost).isEqualTo(List.of(post1, post2, post3));
    }

    @DisplayName("Список постов пользователя")
    @Test
    void whenCheckResultUserPosts() {
        List<User> users = userRepository.findAll();
        Post post1 = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        Post post2 = postRepository.save(createPost(users.get(1), "title", "desc", LocalDateTime.now()));
        Post post3 = postRepository.save(createPost(users.get(0), "title", "desc", LocalDateTime.now()));
        List<Post> allPostByUser1 = postRepository.findByUserId(users.get(0).getId());
        assertThat(allPostByUser1).isEqualTo(List.of(post1, post3));
        List<Post> allPostByUser2 = postRepository.findByUserId(users.get(1).getId());
        assertThat(allPostByUser2).isEqualTo(List.of(post2));
    }

    @DisplayName("Список постов в диапазоне даты")
    @Test
    void whenCheckResultPostsInDateInterval() {
        List<User> users = userRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weeksLate = LocalDateTime.now().minusWeeks(2);
        LocalDateTime monthsLate = LocalDateTime.now().minusMonths(2);
        Post post1 = postRepository.save(createPost(users.get(0), "title", "desc", now));
        Post post2 = postRepository.save(createPost(users.get(1), "title", "desc", weeksLate));
        Post post3 = postRepository.save(createPost(users.get(0), "title", "desc", monthsLate));
        List<Post> result1 = postRepository.findByCreatedBetween(
                LocalDateTime.now().minusWeeks(4),
                LocalDateTime.now().plusDays(10)
                );
        assertThat(result1).isEqualTo(List.of(post1, post2));
        List<Post> result2 = postRepository.findByCreatedBetween(
                LocalDateTime.now().minusYears(1),
                LocalDateTime.now().minusMonths(1)
        );
        assertThat(result2).isEqualTo(List.of(post3));
        List<Post> result3 = postRepository.findByCreatedBetween(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );
        assertThat(result3).isEmpty();
    }

    @DisplayName("Список отсортированный по дате ASC с пагинацией")
    @Test
    void whenCheckSortedByDateAsc() {
        List<User> users = userRepository.findAll();
        Post post1 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(3))
        );
        Post post2 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(1))
        );
        Post post3 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(2))
        );
        Sort sortAsc = Sort.by(Sort.Direction.ASC, "created");
        Page<Post> postPageAsc = postRepository.findAll(PageRequest.of(0, 2).withSort(sortAsc));
        List<Post> postsInPage1Asc = postPageAsc.getContent();
        assertThat(postsInPage1Asc).isEqualTo(List.of(post1, post3));
        postPageAsc = postRepository.findAll(postPageAsc.nextPageable());
        List<Post> postsInPage2Asc = postPageAsc.getContent();
        assertThat(postsInPage2Asc).isEqualTo(List.of(post2));
    }

    @DisplayName("Список отсортированный по дате DESC с пагинацией")
    @Test
    void whenCheckSortedByDateDesc() {
        List<User> users = userRepository.findAll();
        Post post1 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(3))
        );
        Post post2 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(1))
        );
        Post post3 = postRepository.save(
                createPost(users.get(0), "title", "desc", LocalDateTime.now().minusDays(2))
        );
        Sort sortDesc = Sort.by(Sort.Direction.DESC, "created");
        Page<Post> postPageDesk = postRepository.findAll(PageRequest.of(0, 2).withSort(sortDesc));
        List<Post> postsInPage1Desc = postPageDesk.getContent();
        assertThat(postsInPage1Desc).isEqualTo(List.of(post2, post3));
        postPageDesk = postRepository.findAll(postPageDesk.nextPageable());
        List<Post> postsInPage2Desc = postPageDesk.getContent();
        assertThat(postsInPage2Desc).isEqualTo(List.of(post1));
    }

    private Post createPost(User user, String title, String description, LocalDateTime dateTime) {
        return Post.builder()
                .user(user)
                .title(title)
                .description(description)
                .created(dateTime)
                .build();
    }
}