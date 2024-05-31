package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.model.Post;

public interface PostRepository extends ListCrudRepository<Post, Integer> {
}
