package ru.diasoft.spring.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.CommentRequest;

import java.util.List;

@FeignClient(name = "book-service-comments", url = "${book-service.url}")
public interface CommentFeignClient {

    @GetMapping("/api/books/{bookId}/comments")
    List<CommentDto> getCommentsByBook(@PathVariable("bookId") Long bookId);

    @PostMapping("/api/books/{bookId}/comments")
    CommentDto createComment(@PathVariable("bookId") Long bookId, @RequestBody CommentRequest request);

    @GetMapping("/api/comments/{id}")
    CommentDto getCommentById(@PathVariable("id") Long id);

    @PutMapping("/api/comments/{id}")
    CommentDto updateComment(@PathVariable("id") Long id, @RequestBody CommentRequest request);

    @DeleteMapping("/api/comments/{id}")
    void deleteComment(@PathVariable("id") Long id);
}
