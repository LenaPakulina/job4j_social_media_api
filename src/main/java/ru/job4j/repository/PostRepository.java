package ru.job4j.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;
import ru.job4j.model.Subscriber;

import java.time.LocalDateTime;
import java.util.List;

@EnableJpaRepositories
public interface PostRepository extends ListCrudRepository<Post, Integer> {
    List<Post> findByUserId(Integer id);

    List<Post> findByCreatedBetween(LocalDateTime startAt, LocalDateTime finishAt);

    Page<Post> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post SET title = :title, description = :desc
            WHERE id = :id
            """)
    int updateTitleAndDesc(@Param("title") String title,
                               @Param("desc") String desc,
                               @Param("id") int id);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete File where id = ?1
            """)
    boolean deleteFileById(int id);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete Post where id = ?1
            """)
    boolean deletePostById(int id);

    @Query("""
        SELECT post FROM Post post
        JOIN Subscriber sub ON post.user.id = sub.toUser.id
        WHERE sub.fromUser.id = :userId
        """)
    Page<Post> findPostsByFollowers(@Param("userId") int userId, Pageable pageable);
}
