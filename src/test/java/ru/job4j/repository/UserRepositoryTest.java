package ru.job4j.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.model.Post;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clearStorage() {
        userRepository.deleteAll();
    }

    @DisplayName("Сохранение")
    @Test
    void whenSavePost() {
        User user = userRepository.save(
                User.builder()
                .name("name")
                .email("email1")
                .password("password")
                .build()
        );
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPassword()).isEqualTo(user.getPassword());
    }
}