package ru.diasoft.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Репозиторий для работы с книгами")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("findByIdWithDetails должен загружать книгу вместе с автором и жанром")
    void shouldFindByIdWithDetails() {
        Optional<Book> book = bookRepository.findByIdWithDetails(1L);

        assertThat(book).isPresent();
        assertThat(book.get().getTitle()).isEqualTo("Test Book 1");
        assertThat(book.get().getAuthor().getName()).isEqualTo("Test Author 1");
        assertThat(book.get().getGenre().getName()).isEqualTo("Test Genre 1");
    }

    @Test
    @DisplayName("findByIdWithDetails должен возвращать empty если книга не найдена")
    void shouldReturnEmptyIfNotFound() {
        assertThat(bookRepository.findByIdWithDetails(999L)).isEmpty();
    }

    @Test
    @DisplayName("findAllWithDetails должен загружать все книги с автором и жанром")
    void shouldFindAllWithDetails() {
        List<Book> books = bookRepository.findAllWithDetails();

        assertThat(books).hasSize(3);
        assertThat(books).allMatch(b -> b.getAuthor() != null && b.getGenre() != null);
    }

    @Test
    @DisplayName("findByAuthorId должен возвращать книги по автору")
    void shouldFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorId(1L);

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Test Book 1", "Test Book 2");
    }

    @Test
    @DisplayName("findByGenreId должен возвращать книги по жанру")
    void shouldFindByGenreId() {
        List<Book> books = bookRepository.findByGenreId(1L);

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Test Book 1", "Test Book 3");
    }
}
