package ru.job4j.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends ListCrudRepository<Post, Integer> {
    List<Post> findByUserId(Integer id);

    List<Post> findByCreatedBetween(LocalDateTime startAt, LocalDateTime finishAt);

    Page<Post> findAll(Pageable pageable);
}
