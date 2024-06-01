package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Integer> {
}
