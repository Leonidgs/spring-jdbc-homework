package ru.diasoft.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Author;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookDao.class, AuthorDao.class, GenreDao.class})
@ActiveProfiles("test")
@DisplayName("DAO для работы с книгами")
class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("должен находить книгу по id")
    void shouldFindBookById() {
        Optional<Book> book = bookDao.findById(1L);

        assertThat(book).isPresent();
        assertThat(book.get().getId()).isEqualTo(1L);
        assertThat(book.get().getTitle()).isEqualTo("Test Book 1");
        assertThat(book.get().getAuthor().getName()).isEqualTo("Test Author 1");
        assertThat(book.get().getGenre().getName()).isEqualTo("Test Genre 1");
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
        Author author = authorDao.findById(1L).orElseThrow();
        Genre genre = genreDao.findById(1L).orElseThrow();
        Book newBook = Book.builder()
                .title("New Book")
                .author(author)
                .genre(genre)
                .build();

        Book saved = bookDao.insert(newBook);

        assertThat(saved.getId()).isNotNull();
        Optional<Book> savedBook = bookDao.findById(saved.getId());
        assertThat(savedBook).isPresent();
        assertThat(savedBook.get().getTitle()).isEqualTo("New Book");
    }

    @Test
    @DisplayName("должен обновлять книгу")
    void shouldUpdateBook() {
        Book book = bookDao.findById(1L).orElseThrow();
        Author author2 = authorDao.findById(2L).orElseThrow();
        Genre genre2 = genreDao.findById(2L).orElseThrow();
        book.setTitle("Updated Book");
        book.setAuthor(author2);
        book.setGenre(genre2);

        bookDao.update(book);

        Optional<Book> updatedBook = bookDao.findById(1L);
        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get().getTitle()).isEqualTo("Updated Book");
        assertThat(updatedBook.get().getAuthor().getId()).isEqualTo(2L);
        assertThat(updatedBook.get().getGenre().getId()).isEqualTo(2L);
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
