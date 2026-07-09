package ru.diasoft.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Сервис для работы с комментариями")
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("должен создавать комментарий")
    @DirtiesContext
    void shouldCreateComment() {
        Long id = commentService.createComment("Great book!", 1L);

        assertThat(id).isNotNull();
        Comment created = commentService.getCommentById(id);
        assertThat(created.getText()).isEqualTo("Great book!");
        assertThat(created.getBook().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("должен выбрасывать исключение при создании комментария к несуществующей книге")
    void shouldThrowWhenBookNotFound() {
        assertThatThrownBy(() -> commentService.createComment("text", 999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    @DisplayName("должен выбрасывать исключение если комментарий не найден")
    void shouldThrowIfCommentNotFound() {
        assertThatThrownBy(() -> commentService.getCommentById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Comment not found");
    }

    @Test
    @DisplayName("должен возвращать комментарии по id книги")
    @DirtiesContext
    void shouldGetCommentsByBookId() {
        commentService.createComment("Comment 1", 1L);
        commentService.createComment("Comment 2", 1L);

        List<Comment> comments = commentService.getCommentsByBookId(1L);

        assertThat(comments).hasSize(2);
    }

    @Test
    @DisplayName("должен обновлять комментарий")
    @DirtiesContext
    void shouldUpdateComment() {
        Long id = commentService.createComment("Original", 1L);
        commentService.updateComment(id, "Updated");

        Comment updated = commentService.getCommentById(id);
        assertThat(updated.getText()).isEqualTo("Updated");
    }

    @Test
    @DisplayName("должен удалять комментарий")
    @DirtiesContext
    void shouldDeleteComment() {
        Long id = commentService.createComment("To delete", 1L);
        commentService.deleteComment(id);

        assertThatThrownBy(() -> commentService.getCommentById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Comment not found");
    }
}
