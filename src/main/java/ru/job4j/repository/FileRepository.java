package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.File;

public interface FileRepository extends ListCrudRepository<File, Integer> {
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE FROM File file WHERE file.post_id = :id
            """, nativeQuery = true)
    void deleteAllFilesByPostId(@Param("id") int postId);
}
