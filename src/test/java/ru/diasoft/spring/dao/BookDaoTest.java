package ru.diasoft.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDao.class, AuthorDao.class, GenreDao.class})
@ActiveProfiles("test")
@DisplayName("DAO для работы с книгами")
class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("должен находить книгу по id")
    void shouldFindBookById() {
        Optional<Book> book = bookDao.findById(1L);

        assertThat(book).isPresent();
        assertThat(book.get().getId()).isEqualTo(1L);
        assertThat(book.get().getTitle()).isEqualTo("Test Book 1");
        assertThat(book.get().getAuthorId()).isEqualTo(1L);
        assertThat(book.get().getGenreId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("должен возвращать empty optional если книга не найдена")
    void shouldReturnEmptyIfBookNotFound() {
        Optional<Book> book = bookDao.findById(999L);

        assertThat(book).isEmpty();
    }

    @Test
    @DisplayName("должен возвращать все книги")
    void shouldReturnAllBooks() {
        List<Book> books = bookDao.findAll();

        assertThat(books).hasSize(3);
    }

    @Test
    @DisplayName("должен сохранять новую книгу")
    void shouldSaveNewBook() {
        Book newBook = Book.builder()
                .title("New Book")
                .authorId(1L)
                .genreId(1L)
                .build();

        Long id = bookDao.insert(newBook);

        assertThat(id).isNotNull();
        Optional<Book> savedBook = bookDao.findById(id);
        assertThat(savedBook).isPresent();
        assertThat(savedBook.get().getTitle()).isEqualTo("New Book");
    }

    @Test
    @DisplayName("должен обновлять книгу")
    void shouldUpdateBook() {
        Book book = Book.builder()
                .id(1L)
                .title("Updated Book")
                .authorId(2L)
                .genreId(2L)
                .build();

        bookDao.update(book);

        Optional<Book> updatedBook = bookDao.findById(1L);
        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get().getTitle()).isEqualTo("Updated Book");
        assertThat(updatedBook.get().getAuthorId()).isEqualTo(2L);
        assertThat(updatedBook.get().getGenreId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("должен удалять книгу")
    void shouldDeleteBook() {
        bookDao.deleteById(3L);

        Optional<Book> deletedBook = bookDao.findById(3L);
        assertThat(deletedBook).isEmpty();
    }

    @Test
    @DisplayName("должен находить книги по id автора")
    void shouldFindBooksByAuthorId() {
        List<Book> books = bookDao.findByAuthorId(1L);

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Test Book 1", "Test Book 2");
    }

    @Test
    @DisplayName("должен находить книги по id жанра")
    void shouldFindBooksByGenreId() {
        List<Book> books = bookDao.findByGenreId(1L);

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Test Book 1", "Test Book 3");
    }

    @Test
    @DisplayName("должен проверять существование книги")
    void shouldCheckBookExists() {
        assertThat(bookDao.existsById(1L)).isTrue();
        assertThat(bookDao.existsById(999L)).isFalse();
    }
}
