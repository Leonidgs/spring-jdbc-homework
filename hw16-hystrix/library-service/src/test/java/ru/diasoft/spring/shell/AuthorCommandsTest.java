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
@DisplayName("Команды Shell для работы с авторами")
class AuthorCommandsTest {

    @Autowired
    private AuthorCommands authorCommands;

    @Test
    @DisplayName("должен отображать список авторов")
    void shouldListAuthors() {
        String result = authorCommands.listAuthors();

        assertThat(result).contains("Test Author 1", "Test Author 2");
    }

    @Test
    @DisplayName("должен находить автора по id")
    void shouldGetAuthorById() {
        String result = authorCommands.getAuthor(1L);

        assertThat(result).contains("Test Author 1");
    }

    @Test
    @DisplayName("должен создавать нового автора")
    @DirtiesContext
    void shouldCreateAuthor() {
        String result = authorCommands.createAuthor("Shell Test Author");

        assertThat(result).contains("Author created with id:");
    }

    @Test
    @DisplayName("должен обновлять автора")
    @DirtiesContext
    void shouldUpdateAuthor() {
        String result = authorCommands.updateAuthor(1L, "Updated by Shell");

        assertThat(result).contains("Author with id 1 updated");
    }

    @Test
    @DisplayName("должен удалять автора")
    @DirtiesContext
    void shouldDeleteAuthor() {
        String result = authorCommands.deleteAuthor(2L);

        assertThat(result).contains("Author with id 2 deleted");
    }
}
