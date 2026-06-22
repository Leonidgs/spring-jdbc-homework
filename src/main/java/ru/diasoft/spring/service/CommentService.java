package ru.diasoft.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.dao.BookDao;
import ru.diasoft.spring.dao.CommentDao;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.domain.Comment;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Transactional
    public Long createComment(String text, Long bookId) {
        Book book = bookDao.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        Comment comment = Comment.builder()
                .text(text)
                .book(book)
                .build();
        return commentDao.insert(comment).getId();
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(Long id) {
        return commentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(Long bookId) {
        return commentDao.findByBookId(bookId);
    }

    @Transactional
    public void updateComment(Long id, String text) {
        Comment comment = commentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        comment.setText(text);
        commentDao.update(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }
}
