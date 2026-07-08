package ru.diasoft.spring.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.service.BookServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class BookClientController {

    private final BookServiceClient bookServiceClient;

    @PostMapping("/books/kafka")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createBookViaKafka(@RequestParam String title,
                                   @RequestParam Long authorId,
                                   @RequestParam Long genreId) {
        bookServiceClient.createBookViaKafka(title, authorId, genreId);
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookServiceClient.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookServiceClient.getBookById(id);
    }

    @GetMapping("/books/{bookId}/comments")
    public List<CommentDto> getCommentsByBook(@PathVariable Long bookId) {
        return bookServiceClient.getCommentsByBookId(bookId);
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        return bookServiceClient.getAllAuthors();
    }

    @GetMapping("/authors/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        return bookServiceClient.getAuthorById(id);
    }

    @GetMapping("/genres")
    public List<GenreDto> getAllGenres() {
        return bookServiceClient.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return bookServiceClient.getGenreById(id);
    }
}
