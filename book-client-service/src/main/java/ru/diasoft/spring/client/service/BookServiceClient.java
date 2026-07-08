package ru.diasoft.spring.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.kafka.BookRequestProducer;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceClient {

    private final RestTemplate restTemplate;
    private final BookRequestProducer bookRequestProducer;

    @Value("${book-service.url}")
    private String bookServiceUrl;

    // === Books ===

    @Cacheable("books")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<BookDto> getAllBooks() {
        log.info("Fetching all books from {}", bookServiceUrl);
        ResponseEntity<List<BookDto>> response = restTemplate.exchange(
                bookServiceUrl + "/api/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Cacheable(value = "books", key = "#id")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public BookDto getBookById(Long id) {
        log.info("Fetching book {} from {}", id, bookServiceUrl);
        return restTemplate.getForObject(bookServiceUrl + "/api/books/{id}", BookDto.class, id);
    }

    @CacheEvict(value = "books", allEntries = true)
    public BookDto createBook(String title, Long authorId, Long genreId) {
        log.info("Creating book '{}' via {}", title, bookServiceUrl);
        var request = java.util.Map.of("title", title, "authorId", authorId, "genreId", genreId);
        return restTemplate.postForObject(bookServiceUrl + "/api/books", request, BookDto.class);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void createBookViaKafka(String title, Long authorId, Long genreId) {
        log.info("Sending create-book request via Kafka: title='{}', authorId={}, genreId={}", title, authorId, genreId);
        bookRequestProducer.sendCreateBookRequest(title, authorId, genreId);
    }

    // === Authors ===

    @Cacheable("authors")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<AuthorDto> getAllAuthors() {
        log.info("Fetching all authors from {}", bookServiceUrl);
        ResponseEntity<List<AuthorDto>> response = restTemplate.exchange(
                bookServiceUrl + "/api/authors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Cacheable(value = "authors", key = "#id")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public AuthorDto getAuthorById(Long id) {
        log.info("Fetching author {} from {}", id, bookServiceUrl);
        return restTemplate.getForObject(bookServiceUrl + "/api/authors/{id}", AuthorDto.class, id);
    }

    // === Genres ===

    @Cacheable("genres")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<GenreDto> getAllGenres() {
        log.info("Fetching all genres from {}", bookServiceUrl);
        ResponseEntity<List<GenreDto>> response = restTemplate.exchange(
                bookServiceUrl + "/api/genres",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    @Cacheable(value = "genres", key = "#id")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public GenreDto getGenreById(Long id) {
        log.info("Fetching genre {} from {}", id, bookServiceUrl);
        return restTemplate.getForObject(bookServiceUrl + "/api/genres/{id}", GenreDto.class, id);
    }

    // === Comments ===

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<CommentDto> getCommentsByBookId(Long bookId) {
        log.info("Fetching comments for book {} from {}", bookId, bookServiceUrl);
        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(
                bookServiceUrl + "/api/books/{bookId}/comments",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                bookId
        );
        return response.getBody();
    }
}
