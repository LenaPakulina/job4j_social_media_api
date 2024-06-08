package ru.job4j.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.job4j.model.Subscriber;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @AfterEach
    void clearStorage() {
        subscriberRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("Сохранение")
    @Test
    void whenSavePost() {
        User user = userRepository.save(createUser("name", "email", "password"));
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getPassword()).isEqualTo(user.getPassword());
    }

    @DisplayName("Поиск пользователя по логину и паролю")
    @Test
    void whenFindByEmailAndPassword() {
        User user = userRepository.save(createUser("name", "email", "password"));
        Optional<User> result = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.get().getPassword()).isEqualTo(user.getPassword());
    }

    @DisplayName("Список всех подписчиков пользователя")
    @Test
    void whenFindAllSubscribersById() {
        User user1 = userRepository.save(createUser("name", "email1", "password1"));
        User user2 = userRepository.save(createUser("name", "email2", "password"));
        User user3 = userRepository.save(createUser("name", "email3", "password"));
        User user4 = userRepository.save(createUser("name", "email4", "password"));
        Subscriber elem1 = subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user2).build());
        Subscriber elem2 = subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user3).build());
        Subscriber elem3 = subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user4).build());
        Subscriber elem4 = subscriberRepository.save(Subscriber.builder().fromUser(user2).toUser(user1).build());
        List<Subscriber> resultUser1 = userRepository.findAllSubscribersById(user1.getId());
        assertThat(resultUser1).isEqualTo(List.of(elem1, elem2, elem3));
        List<Subscriber> resultUser2 = userRepository.findAllSubscribersById(user2.getId());
        assertThat(resultUser2).isEqualTo(List.of(elem4));
        List<Subscriber> resultUser3 = userRepository.findAllSubscribersById(user3.getId());
        assertThat(resultUser3).isEmpty();
    }

    @DisplayName("Список всех друзей пользователя")
    @Test
    void whenFindAllFriendsById() {
        User user1 = userRepository.save(createUser("name", "email1", "password1"));
        User user2 = userRepository.save(createUser("name", "email2", "password"));
        User user3 = userRepository.save(createUser("name", "email3", "password"));
        User user4 = userRepository.save(createUser("name", "email4", "password"));
        subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user2).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user3).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user1).toUser(user4).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user2).toUser(user4).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user4).toUser(user2).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user3).toUser(user2).build());
        subscriberRepository.save(Subscriber.builder().fromUser(user2).toUser(user1).build());
        List<User> resultUser1 = userRepository.findAllFriendsById(user1.getId());
        assertThat(resultUser1).isEqualTo(List.of(user2));
        List<User> resultUser2 = userRepository.findAllFriendsById(user2.getId());
        assertThat(resultUser2).isEqualTo(List.of(user1, user4));
    }

    private User createUser(String name, String email, String password) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}