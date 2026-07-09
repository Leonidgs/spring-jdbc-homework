package ru.diasoft.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Сервис для работы с авторами")
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("должен создавать нового автора")
    @DirtiesContext
    void shouldCreateAuthor() {
        Long id = authorService.createAuthor("New Service Author");

        assertThat(id).isNotNull();
        Author created = authorService.getAuthorById(id);
        assertThat(created.getName()).isEqualTo("New Service Author");
    }

    @Test
    @DisplayName("должен находить автора по id")
    void shouldGetAuthorById() {
        Author author = authorService.getAuthorById(1L);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Test Author 1");
    }

    @Test
    @DisplayName("должен выбрасывать исключение если автор не найден")
    void shouldThrowIfAuthorNotFound() {
        assertThatThrownBy(() -> authorService.getAuthorById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    @DisplayName("должен возвращать всех авторов")
    void shouldGetAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();

        assertThat(authors).hasSize(2);
    }

    @Test
    @DisplayName("должен обновлять автора")
    @DirtiesContext
    void shouldUpdateAuthor() {
        authorService.updateAuthor(1L, "Updated Name");

        Author updated = authorService.getAuthorById(1L);
        assertThat(updated.getName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("должен удалять автора")
    @DirtiesContext
    void shouldDeleteAuthor() {
        authorService.deleteAuthor(2L);

        assertThatThrownBy(() -> authorService.getAuthorById(2L))
                .isInstanceOf(RuntimeException.class);
    }
}
