package ru.diasoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.spring.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
