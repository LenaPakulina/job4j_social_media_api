package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.model.Subscriber;

public interface SubscriberRepository extends ListCrudRepository<Subscriber, Integer> {
}
