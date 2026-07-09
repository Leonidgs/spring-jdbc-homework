package ru.diasoft.spring.client.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.NameRequest;
import ru.diasoft.spring.client.feign.AuthorFeignClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorClientService {

    private static final String CIRCUIT_BREAKER_NAME = "bookService";

    private final AuthorFeignClient authorFeignClient;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAllAuthorsFallback")
    public List<AuthorDto> getAllAuthors() {
        return authorFeignClient.getAllAuthors();
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAuthorByIdFallback")
    public AuthorDto getAuthorById(Long id) {
        return authorFeignClient.getAuthorById(id);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "createAuthorFallback")
    public AuthorDto createAuthor(NameRequest request) {
        return authorFeignClient.createAuthor(request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateAuthorFallback")
    public AuthorDto updateAuthor(Long id, NameRequest request) {
        return authorFeignClient.updateAuthor(id, request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteAuthorFallback")
    public void deleteAuthor(Long id) {
        authorFeignClient.deleteAuthor(id);
    }

    private List<AuthorDto> getAllAuthorsFallback(Throwable t) {
        log.warn("Circuit breaker triggered for getAllAuthors: {}", t.getMessage());
        return Collections.emptyList();
    }

    private AuthorDto getAuthorByIdFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for getAuthorById({}): {}", id, t.getMessage());
        return AuthorDto.builder()
                .id(id)
                .name("N/A — book-service unavailable")
                .build();
    }

    private AuthorDto createAuthorFallback(NameRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for createAuthor: {}", t.getMessage());
        return AuthorDto.builder()
                .name("N/A — book-service unavailable")
                .build();
    }

    private AuthorDto updateAuthorFallback(Long id, NameRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for updateAuthor({}): {}", id, t.getMessage());
        return AuthorDto.builder()
                .id(id)
                .name("N/A — book-service unavailable")
                .build();
    }

    private void deleteAuthorFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for deleteAuthor({}): {}", id, t.getMessage());
    }
}
