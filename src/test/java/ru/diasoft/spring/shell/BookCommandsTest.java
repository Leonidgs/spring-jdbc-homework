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
@DisplayName("Команды Shell для работы с книгами")
class BookCommandsTest {

    @Autowired
    private BookCommands bookCommands;

    @Test
    @DisplayName("должен отображать список книг")
    void shouldListBooks() {
        String result = bookCommands.listBooks();

        assertThat(result).contains("Test Book 1", "Test Book 2", "Test Book 3");
    }

    @Test
    @DisplayName("должен находить книгу по id")
    void shouldGetBookById() {
        String result = bookCommands.getBook(1L);

        assertThat(result).contains("Test Book 1", "Author ID: 1", "Genre ID: 1");
    }

    @Test
    @DisplayName("должен создавать новую книгу")
    @DirtiesContext
    void shouldCreateBook() {
        String result = bookCommands.createBook("Shell Test Book", 1L, 1L);

        assertThat(result).contains("Book created with id:");
    }

    @Test
    @DisplayName("должен обновлять книгу")
    @DirtiesContext
    void shouldUpdateBook() {
        String result = bookCommands.updateBook(1L, "Updated by Shell", 2L, 2L);

        assertThat(result).contains("Book with id 1 updated");
    }

    @Test
    @DisplayName("должен удалять книгу")
    @DirtiesContext
    void shouldDeleteBook() {
        String result = bookCommands.deleteBook(3L);

        assertThat(result).contains("Book with id 3 deleted");
    }

    @Test
    @DisplayName("должен находить книги по автору")
    void shouldGetBooksByAuthor() {
        String result = bookCommands.getBooksByAuthor(1L);

        assertThat(result).contains("Test Book 1", "Test Book 2");
    }

    @Test
    @DisplayName("должен находить книги по жанру")
    void shouldGetBooksByGenre() {
        String result = bookCommands.getBooksByGenre(1L);

        assertThat(result).contains("Test Book 1", "Test Book 3");
    }
}
