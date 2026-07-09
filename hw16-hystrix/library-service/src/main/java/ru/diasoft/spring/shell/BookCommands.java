package ru.diasoft.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.service.BookService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService bookService;

    @ShellMethod(value = "Create a new book", key = "book-create")
    public String createBook(
            @ShellOption(value = {"-t", "--title"}, help = "Book title") String title,
            @ShellOption(value = {"-a", "--author"}, help = "Author ID") Long authorId,
            @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") Long genreId) {
        Long bookId = bookService.createBook(title, authorId, genreId);
        return String.format("Book created with id: %d", bookId);
    }

    @ShellMethod(value = "Get book by id", key = "book-get")
    public String getBook(@ShellOption(help = "Book ID") Long id) {
        Book book = bookService.getBookById(id);
        return formatBook(book);
    }

    @ShellMethod(value = "List all books", key = "book-list")
    public String listBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return "No books found";
        }
        StringBuilder sb = new StringBuilder("Books:\n");
        for (Book book : books) {
            sb.append(formatBook(book)).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Update book", key = "book-update")
    public String updateBook(
            @ShellOption(help = "Book ID") Long id,
            @ShellOption(value = {"-t", "--title"}, help = "Book title") String title,
            @ShellOption(value = {"-a", "--author"}, help = "Author ID") Long authorId,
            @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") Long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
        return String.format("Book with id %d updated", id);
    }

    @ShellMethod(value = "Delete book", key = "book-delete")
    public String deleteBook(@ShellOption(help = "Book ID") Long id) {
        bookService.deleteBook(id);
        return String.format("Book with id %d deleted", id);
    }

    @ShellMethod(value = "Get books by author", key = "book-by-author")
    public String getBooksByAuthor(@ShellOption(help = "Author ID") Long authorId) {
        List<Book> books = bookService.getBooksByAuthor(authorId);
        if (books.isEmpty()) {
            return "No books found for this author";
        }
        StringBuilder sb = new StringBuilder("Books by author:\n");
        for (Book book : books) {
            sb.append(formatBook(book)).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Get books by genre", key = "book-by-genre")
    public String getBooksByGenre(@ShellOption(help = "Genre ID") Long genreId) {
        List<Book> books = bookService.getBooksByGenre(genreId);
        if (books.isEmpty()) {
            return "No books found for this genre";
        }
        StringBuilder sb = new StringBuilder("Books by genre:\n");
        for (Book book : books) {
            sb.append(formatBook(book)).append("\n");
        }
        return sb.toString();
    }

    private String formatBook(Book book) {
        return String.format("ID: %d, Title: %s, Author: %s, Genre: %s",
                book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
    }
}
