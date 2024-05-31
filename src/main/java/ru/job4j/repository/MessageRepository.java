package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.model.Message;

public interface MessageRepository extends ListCrudRepository<Message, Integer> {
}
