package ru.diasoft.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.diasoft.spring.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(BookDto::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return BookDto.from(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookRequest request) {
        Long id = bookService.createBook(request.getTitle(), request.getAuthorId(), request.getGenreId());
        BookDto created = BookDto.from(bookService.getBookById(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        bookService.updateBook(id, request.getTitle(), request.getAuthorId(), request.getGenreId());
        return BookDto.from(bookService.getBookById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
