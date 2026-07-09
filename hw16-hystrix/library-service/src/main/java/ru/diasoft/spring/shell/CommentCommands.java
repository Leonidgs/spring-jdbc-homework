package ru.diasoft.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.domain.Comment;
import ru.diasoft.spring.service.CommentService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    private final CommentService commentService;

    @ShellMethod(value = "Add comment to book", key = "comment-add")
    public String addComment(
            @ShellOption(value = {"-t", "--text"}, help = "Comment text") String text,
            @ShellOption(value = {"-b", "--book"}, help = "Book ID") Long bookId) {
        Long id = commentService.createComment(text, bookId);
        return String.format("Comment created with id: %d", id);
    }

    @ShellMethod(value = "Get comment by id", key = "comment-get")
    public String getComment(@ShellOption(help = "Comment ID") Long id) {
        Comment comment = commentService.getCommentById(id);
        return formatComment(comment);
    }

    @ShellMethod(value = "List comments for book", key = "comment-list")
    public String listComments(@ShellOption(help = "Book ID") Long bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        if (comments.isEmpty()) {
            return "No comments found for this book";
        }
        StringBuilder sb = new StringBuilder("Comments:\n");
        for (Comment comment : comments) {
            sb.append(formatComment(comment)).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Update comment", key = "comment-update")
    public String updateComment(
            @ShellOption(help = "Comment ID") Long id,
            @ShellOption(value = {"-t", "--text"}, help = "New text") String text) {
        commentService.updateComment(id, text);
        return String.format("Comment with id %d updated", id);
    }

    @ShellMethod(value = "Delete comment", key = "comment-delete")
    public String deleteComment(@ShellOption(help = "Comment ID") Long id) {
        commentService.deleteComment(id);
        return String.format("Comment with id %d deleted", id);
    }

    private String formatComment(Comment comment) {
        return String.format("ID: %d, Book: %s, Text: %s",
                comment.getId(), comment.getBook().getTitle(), comment.getText());
    }
}
