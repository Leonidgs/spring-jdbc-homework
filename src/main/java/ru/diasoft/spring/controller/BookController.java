package ru.diasoft.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.dto.BookDto;
import ru.diasoft.spring.dto.BookRequest;
import ru.diasoft.spring.dto.CommentDto;
import ru.diasoft.spring.dto.CommentRequest;
import ru.diasoft.spring.service.BookService;
import ru.diasoft.spring.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return BookDto.fromDomain(bookService.getBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookRequest request) {
        Long id = bookService.createBook(request.getTitle(), request.getAuthorId(), request.getGenreId());
        return BookDto.fromDomain(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        bookService.updateBook(id, request.getTitle(), request.getAuthorId(), request.getGenreId());
        return BookDto.fromDomain(bookService.getBookById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{bookId}/comments")
    public List<CommentDto> getCommentsByBook(@PathVariable Long bookId) {
        return commentService.getCommentsByBookId(bookId).stream()
                .map(CommentDto::fromDomain)
                .toList();
    }

    @PostMapping("/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long bookId, @RequestBody CommentRequest request) {
        Long commentId = commentService.createComment(request.getText(), bookId);
        return CommentDto.fromDomain(commentService.getCommentById(commentId));
    }

    @PutMapping("/{bookId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable Long bookId,
                                    @PathVariable Long commentId,
                                    @RequestBody CommentRequest request) {
        commentService.updateComment(commentId, request.getText());
        return CommentDto.fromDomain(commentService.getCommentById(commentId));
    }

    @DeleteMapping("/{bookId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long bookId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
