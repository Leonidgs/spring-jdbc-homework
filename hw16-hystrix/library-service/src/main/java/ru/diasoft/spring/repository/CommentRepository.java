package ru.diasoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.diasoft.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.book WHERE c.id = :id")
    Optional<Comment> findByIdWithBook(Long id);

    @Query("SELECT c FROM Comment c JOIN FETCH c.book WHERE c.book.id = :bookId")
    List<Comment> findByBookId(Long bookId);
}
