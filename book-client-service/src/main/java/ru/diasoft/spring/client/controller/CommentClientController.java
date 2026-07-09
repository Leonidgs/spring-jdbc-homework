package ru.diasoft.spring.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.CommentRequest;
import ru.diasoft.spring.client.service.CommentClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentClientController {

    private final CommentClientService commentClientService;

    @GetMapping("/api/books/{bookId}/comments")
    public List<CommentDto> getCommentsByBook(@PathVariable Long bookId) {
        return commentClientService.getCommentsByBook(bookId);
    }

    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long bookId,
                                                    @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentClientService.createComment(bookId, request));
    }

    @GetMapping("/api/comments/{id}")
    public CommentDto getCommentById(@PathVariable Long id) {
        return commentClientService.getCommentById(id);
    }

    @PutMapping("/api/comments/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentClientService.updateComment(id, request);
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentClientService.deleteComment(id);
    }
}
