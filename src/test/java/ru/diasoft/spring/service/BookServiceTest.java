package ru.diasoft.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Сервис для работы с книгами")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("должен создавать новую книгу")
    @DirtiesContext
    void shouldCreateBook() {
        Long id = bookService.createBook("New Service Book", 1L, 1L);

        assertThat(id).isNotNull();
        Book created = bookService.getBookById(id);
        assertThat(created.getTitle()).isEqualTo("New Service Book");
    }

    @Test
    @DisplayName("должен выбрасывать исключение при создании книги с несуществующим автором")
    void shouldThrowWhenCreatingBookWithInvalidAuthor() {
        assertThatThrownBy(() -> bookService.createBook("Title", 999L, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    @DisplayName("должен выбрасывать исключение при создании книги с несуществующим жанром")
    void shouldThrowWhenCreatingBookWithInvalidGenre() {
        assertThatThrownBy(() -> bookService.createBook("Title", 1L, 999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Genre not found");
    }

    @Test
    @DisplayName("должен находить книгу по id")
    void shouldGetBookById() {
        Book book = bookService.getBookById(1L);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Test Book 1");
    }

    @Test
    @DisplayName("должен выбрасывать исключение если книга не найдена")
    void shouldThrowIfBookNotFound() {
        assertThatThrownBy(() -> bookService.getBookById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    @DisplayName("должен возвращать все книги")
    void shouldGetAllBooks() {
        List<Book> books = bookService.getAllBooks();

        assertThat(books).hasSize(3);
    }

    @Test
    @DisplayName("должен обновлять книгу")
    @DirtiesContext
    void shouldUpdateBook() {
        bookService.updateBook(1L, "Updated Book Title", 2L, 2L);

        Book updated = bookService.getBookById(1L);
        assertThat(updated.getTitle()).isEqualTo("Updated Book Title");
        assertThat(updated.getAuthorId()).isEqualTo(2L);
        assertThat(updated.getGenreId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("должен удалять книгу")
    @DirtiesContext
    void shouldDeleteBook() {
        bookService.deleteBook(3L);

        assertThatThrownBy(() -> bookService.getBookById(3L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("должен находить книги по id автора")
    void shouldGetBooksByAuthor() {
        List<Book> books = bookService.getBooksByAuthor(1L);

        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("должен находить книги по id жанра")
    void shouldGetBooksByGenre() {
        List<Book> books = bookService.getBooksByGenre(1L);

        assertThat(books).hasSize(2);
    }
}
