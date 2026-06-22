package ru.diasoft.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.dao.AuthorDao;
import ru.diasoft.spring.dao.BookDao;
import ru.diasoft.spring.dao.GenreDao;
import ru.diasoft.spring.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Transactional
    public Long createBook(String title, Long authorId, Long genreId) {
        validateAuthorExists(authorId);
        validateGenreExists(genreId);

        Book book = Book.builder()
                .title(title)
                .authorId(authorId)
                .genreId(genreId)
                .build();
        return bookDao.insert(book);
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Transactional
    public void updateBook(Long id, String title, Long authorId, Long genreId) {
        validateBookExists(id);
        validateAuthorExists(authorId);
        validateGenreExists(genreId);

        Book book = Book.builder()
                .id(id)
                .title(title)
                .authorId(authorId)
                .genreId(genreId)
                .build();
        bookDao.update(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        return bookDao.findByAuthorId(authorId);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByGenre(Long genreId) {
        return bookDao.findByGenreId(genreId);
    }

    private void validateAuthorExists(Long authorId) {
        if (!authorDao.existsById(authorId)) {
            throw new RuntimeException("Author not found with id: " + authorId);
        }
    }

    private void validateGenreExists(Long genreId) {
        if (!genreDao.existsById(genreId)) {
            throw new RuntimeException("Genre not found with id: " + genreId);
        }
    }

    private void validateBookExists(Long bookId) {
        if (!bookDao.existsById(bookId)) {
            throw new RuntimeException("Book not found with id: " + bookId);
        }
    }
}
