package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Subscriber;

@Repository
public interface SubscriberRepository extends ListCrudRepository<Subscriber, Integer> {
}
