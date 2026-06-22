package ru.diasoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.diasoft.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE b.id = :id")
    Optional<Book> findByIdWithDetails(Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre")
    List<Book> findAllWithDetails();

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE b.author.id = :authorId")
    List<Book> findByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b JOIN FETCH b.author JOIN FETCH b.genre WHERE b.genre.id = :genreId")
    List<Book> findByGenreId(Long genreId);
}
