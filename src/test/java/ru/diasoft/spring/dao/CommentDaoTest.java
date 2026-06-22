package ru.diasoft.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentDao.class, BookDao.class, AuthorDao.class, GenreDao.class})
@ActiveProfiles("test")
@DisplayName("DAO для работы с комментариями")
class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("должен сохранять новый комментарий")
    void shouldSaveNewComment() {
        Book book = bookDao.findById(1L).orElseThrow();
        Comment comment = Comment.builder()
                .text("Great book!")
                .book(book)
                .build();

        Comment saved = commentDao.insert(comment);

        assertThat(saved.getId()).isNotNull();
        Optional<Comment> found = commentDao.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getText()).isEqualTo("Great book!");
    }

    @Test
    @DisplayName("должен возвращать empty optional если комментарий не найден")
    void shouldReturnEmptyIfNotFound() {
        Optional<Comment> comment = commentDao.findById(999L);

        assertThat(comment).isEmpty();
    }

    @Test
    @DisplayName("должен находить комментарии по id книги")
    void shouldFindCommentsByBookId() {
        Book book = bookDao.findById(1L).orElseThrow();
        commentDao.insert(Comment.builder().text("Comment 1").book(book).build());
        commentDao.insert(Comment.builder().text("Comment 2").book(book).build());

        List<Comment> comments = commentDao.findByBookId(1L);

        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(Comment::getText)
                .containsExactlyInAnyOrder("Comment 1", "Comment 2");
    }

    @Test
    @DisplayName("должен обновлять комментарий")
    void shouldUpdateComment() {
        Book book = bookDao.findById(1L).orElseThrow();
        Comment saved = commentDao.insert(Comment.builder().text("Original").book(book).build());
        saved.setText("Updated");

        commentDao.update(saved);

        Optional<Comment> updated = commentDao.findById(saved.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getText()).isEqualTo("Updated");
    }

    @Test
    @DisplayName("должен удалять комментарий")
    void shouldDeleteComment() {
        Book book = bookDao.findById(1L).orElseThrow();
        Comment saved = commentDao.insert(Comment.builder().text("To delete").book(book).build());

        commentDao.deleteById(saved.getId());

        assertThat(commentDao.findById(saved.getId())).isEmpty();
    }
}
