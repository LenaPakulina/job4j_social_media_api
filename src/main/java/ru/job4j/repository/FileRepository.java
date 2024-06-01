package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.File;

@Repository
public interface FileRepository extends ListCrudRepository<File, Integer> {
}
