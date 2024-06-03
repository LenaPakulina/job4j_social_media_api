package ru.job4j.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.repository.FileRepository;
import ru.job4j.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(int id) {
        return userRepository.deleteUserById(id) > 0L;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean update(User user) {
        return userRepository.update(user) > 0L;
    }
}
