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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.dto.CommentDto;
import ru.diasoft.spring.dto.CommentRequest;
import ru.diasoft.spring.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/books/{bookId}/comments")
    public List<CommentDto> getCommentsByBook(@PathVariable Long bookId) {
        return commentService.getCommentsByBookId(bookId).stream()
                .map(CommentDto::from)
                .toList();
    }

    @PostMapping("/api/books/{bookId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long bookId,
                                                    @RequestBody CommentRequest request) {
        Long id = commentService.createComment(request.getText(), bookId);
        CommentDto created = CommentDto.from(commentService.getCommentById(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/api/comments/{id}")
    public CommentDto getCommentById(@PathVariable Long id) {
        return CommentDto.from(commentService.getCommentById(id));
    }

    @PutMapping("/api/comments/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        commentService.updateComment(id, request.getText());
        return CommentDto.from(commentService.getCommentById(id));
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
