package ru.diasoft.spring.client.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.BookRequest;
import ru.diasoft.spring.client.feign.BookFeignClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookClientService {

    private static final String CIRCUIT_BREAKER_NAME = "bookService";

    private final BookFeignClient bookFeignClient;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAllBooksFallback")
    public List<BookDto> getAllBooks() {
        return bookFeignClient.getAllBooks();
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getBookByIdFallback")
    public BookDto getBookById(Long id) {
        return bookFeignClient.getBookById(id);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "createBookFallback")
    public BookDto createBook(BookRequest request) {
        return bookFeignClient.createBook(request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateBookFallback")
    public BookDto updateBook(Long id, BookRequest request) {
        return bookFeignClient.updateBook(id, request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteBookFallback")
    public void deleteBook(Long id) {
        bookFeignClient.deleteBook(id);
    }

    private List<BookDto> getAllBooksFallback(Throwable t) {
        log.warn("Circuit breaker triggered for getAllBooks: {}", t.getMessage());
        return Collections.emptyList();
    }

    private BookDto getBookByIdFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for getBookById({}): {}", id, t.getMessage());
        return BookDto.builder()
                .id(id)
                .title("N/A — book-service unavailable")
                .build();
    }

    private BookDto createBookFallback(BookRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for createBook: {}", t.getMessage());
        return BookDto.builder()
                .title("N/A — book-service unavailable")
                .build();
    }

    private BookDto updateBookFallback(Long id, BookRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for updateBook({}): {}", id, t.getMessage());
        return BookDto.builder()
                .id(id)
                .title("N/A — book-service unavailable")
                .build();
    }

    private void deleteBookFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for deleteBook({}): {}", id, t.getMessage());
    }
}
