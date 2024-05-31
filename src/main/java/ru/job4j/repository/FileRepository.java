package ru.job4j.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.model.File;

public interface FileRepository extends ListCrudRepository<File, Integer> {
}
