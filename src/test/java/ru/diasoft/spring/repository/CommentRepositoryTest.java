package ru.diasoft.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Репозиторий для работы с комментариями")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("findByIdWithBook должен загружать комментарий вместе с книгой")
    void shouldFindByIdWithBook() {
        Book book = bookRepository.findById(1L).orElseThrow();
        Comment saved = commentRepository.save(Comment.builder().text("Test comment").book(book).build());

        Optional<Comment> found = commentRepository.findByIdWithBook(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getText()).isEqualTo("Test comment");
        assertThat(found.get().getBook().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findByIdWithBook должен возвращать empty если комментарий не найден")
    void shouldReturnEmptyIfNotFound() {
        assertThat(commentRepository.findByIdWithBook(999L)).isEmpty();
    }

    @Test
    @DisplayName("findByBookId должен возвращать комментарии по id книги")
    void shouldFindByBookId() {
        Book book = bookRepository.findById(1L).orElseThrow();
        commentRepository.save(Comment.builder().text("Comment 1").book(book).build());
        commentRepository.save(Comment.builder().text("Comment 2").book(book).build());

        List<Comment> comments = commentRepository.findByBookId(1L);

        assertThat(comments).hasSize(3);
        assertThat(comments).extracting(Comment::getText)
                .containsExactlyInAnyOrder("Test Comment 1", "Comment 1", "Comment 2");
    }
}
