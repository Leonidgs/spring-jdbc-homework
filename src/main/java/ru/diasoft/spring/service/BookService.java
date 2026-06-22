package ru.diasoft.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.dao.AuthorDao;
import ru.diasoft.spring.dao.BookDao;
import ru.diasoft.spring.dao.GenreDao;
import ru.diasoft.spring.domain.Author;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Transactional
    public Long createBook(String title, Long authorId, Long genreId) {
        Author author = authorDao.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
        Genre genre = genreDao.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));

        Book book = Book.builder()
                .title(title)
                .author(author)
                .genre(genre)
                .build();
        return bookDao.insert(book).getId();
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
        Book book = bookDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        Author author = authorDao.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + authorId));
        Genre genre = genreDao.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId));

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
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
}
