package ru.diasoft.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Команды Shell для работы с комментариями")
class CommentCommandsTest {

    @Autowired
    private CommentCommands commentCommands;

    @Test
    @DisplayName("должен добавлять комментарий к книге")
    @DirtiesContext
    void shouldAddComment() {
        String result = commentCommands.addComment("Nice book", 1L);

        assertThat(result).contains("Comment created with id:");
    }

    @Test
    @DisplayName("должен возвращать список комментариев к книге")
    @DirtiesContext
    void shouldListComments() {
        commentCommands.addComment("Comment 1", 1L);
        commentCommands.addComment("Comment 2", 1L);

        String result = commentCommands.listComments(1L);

        assertThat(result).contains("Comment 1", "Comment 2");
    }

    @Test
    @DisplayName("должен обновлять комментарий")
    @DirtiesContext
    void shouldUpdateComment() {
        String addResult = commentCommands.addComment("Original", 1L);
        Long id = Long.parseLong(addResult.replaceAll("[^0-9]", ""));

        String result = commentCommands.updateComment(id, "Updated text");

        assertThat(result).contains("updated");
    }

    @Test
    @DisplayName("должен удалять комментарий")
    @DirtiesContext
    void shouldDeleteComment() {
        String addResult = commentCommands.addComment("To delete", 1L);
        Long id = Long.parseLong(addResult.replaceAll("[^0-9]", ""));

        String result = commentCommands.deleteComment(id);

        assertThat(result).contains("deleted");
    }
}
