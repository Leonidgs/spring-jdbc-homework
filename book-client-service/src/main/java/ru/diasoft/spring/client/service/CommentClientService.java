package ru.diasoft.spring.client.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.CommentRequest;
import ru.diasoft.spring.client.feign.CommentFeignClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentClientService {

    private static final String CIRCUIT_BREAKER_NAME = "bookService";

    private final CommentFeignClient commentFeignClient;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getCommentsByBookFallback")
    public List<CommentDto> getCommentsByBook(Long bookId) {
        return commentFeignClient.getCommentsByBook(bookId);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "createCommentFallback")
    public CommentDto createComment(Long bookId, CommentRequest request) {
        return commentFeignClient.createComment(bookId, request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getCommentByIdFallback")
    public CommentDto getCommentById(Long id) {
        return commentFeignClient.getCommentById(id);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "updateCommentFallback")
    public CommentDto updateComment(Long id, CommentRequest request) {
        return commentFeignClient.updateComment(id, request);
    }

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "deleteCommentFallback")
    public void deleteComment(Long id) {
        commentFeignClient.deleteComment(id);
    }

    private List<CommentDto> getCommentsByBookFallback(Long bookId, Throwable t) {
        log.warn("Circuit breaker triggered for getCommentsByBook({}): {}", bookId, t.getMessage());
        return Collections.emptyList();
    }

    private CommentDto createCommentFallback(Long bookId, CommentRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for createComment(bookId={}): {}", bookId, t.getMessage());
        return CommentDto.builder()
                .bookId(bookId)
                .text("N/A — book-service unavailable")
                .build();
    }

    private CommentDto getCommentByIdFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for getCommentById({}): {}", id, t.getMessage());
        return CommentDto.builder()
                .id(id)
                .text("N/A — book-service unavailable")
                .build();
    }

    private CommentDto updateCommentFallback(Long id, CommentRequest request, Throwable t) {
        log.warn("Circuit breaker triggered for updateComment({}): {}", id, t.getMessage());
        return CommentDto.builder()
                .id(id)
                .text("N/A — book-service unavailable")
                .build();
    }

    private void deleteCommentFallback(Long id, Throwable t) {
        log.warn("Circuit breaker triggered for deleteComment({}): {}", id, t.getMessage());
    }
}
