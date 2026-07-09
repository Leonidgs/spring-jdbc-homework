package ru.diasoft.spring.client.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.dto.NameRequest;
import ru.diasoft.spring.client.feign.GenreFeignClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreClientService {

    private static final String CIRCUIT_BREAKER_NAME = "bookService";

    private final GenreFeignClient genreFeignClient;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getAllGenresFallback")
    public List<GenreDto> getAllGenres() {
        return genreFeignClient.getAllGenres();
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getGenreByIdFallback")
    public GenreDto getGenreById(Long id) {
        return genreFeignClient.getGenreById(id);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "createGenreFallback")
    public GenreDto createGenre(NameRequest request) {
        return genreFeignClient.createGenre(request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateGenreFallback")
    public GenreDto updateGenre(Long id, NameRequest request) {
        return genreFeignClient.updateGenre(id, request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteGenreFallback")
    public void deleteGenre(Long id) {
        genreFeignClient.deleteGenre(id);
    }

    private List<GenreDto> getAllGenresFallback(Throwable t) {
        log.warn("Circuit breaker triggered for getAllGenres: {}", t.getMessage());
        return Collections.emptyList();
    }

    private GenreDto getGenreByIdFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for getGenreById({}): {}", id, t.getMessage());
        return GenreDto.builder()
                .id(id)
                .name("N/A — book-service unavailable")
                .build();
    }

    private GenreDto createGenreFallback(NameRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for createGenre: {}", t.getMessage());
        return GenreDto.builder()
                .name("N/A — book-service unavailable")
                .build();
    }

    private GenreDto updateGenreFallback(Long id, NameRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for updateGenre({}): {}", id, t.getMessage());
        return GenreDto.builder()
                .id(id)
                .name("N/A — book-service unavailable")
                .build();
    }

    private void deleteGenreFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for deleteGenre({}): {}", id, t.getMessage());
    }
}
