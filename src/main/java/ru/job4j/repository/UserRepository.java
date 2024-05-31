package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.model.User;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
