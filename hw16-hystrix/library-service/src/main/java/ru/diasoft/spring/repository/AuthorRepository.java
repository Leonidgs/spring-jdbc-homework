package ru.diasoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.spring.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
