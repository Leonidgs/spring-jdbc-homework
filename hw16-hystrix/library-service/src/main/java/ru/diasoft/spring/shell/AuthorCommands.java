package ru.diasoft.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.domain.Author;
import ru.diasoft.spring.service.AuthorService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorService authorService;

    @ShellMethod(value = "Create a new author", key = "author-create")
    public String createAuthor(@ShellOption(value = {"-n", "--name"}, help = "Author name") String name) {
        Long authorId = authorService.createAuthor(name);
        return String.format("Author created with id: %d", authorId);
    }

    @ShellMethod(value = "Get author by id", key = "author-get")
    public String getAuthor(@ShellOption(help = "Author ID") Long id) {
        Author author = authorService.getAuthorById(id);
        return formatAuthor(author);
    }

    @ShellMethod(value = "List all authors", key = "author-list")
    public String listAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            return "No authors found";
        }
        StringBuilder sb = new StringBuilder("Authors:\n");
        for (Author author : authors) {
            sb.append(formatAuthor(author)).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Update author", key = "author-update")
    public String updateAuthor(
            @ShellOption(help = "Author ID") Long id,
            @ShellOption(value = {"-n", "--name"}, help = "Author name") String name) {
        authorService.updateAuthor(id, name);
        return String.format("Author with id %d updated", id);
    }

    @ShellMethod(value = "Delete author", key = "author-delete")
    public String deleteAuthor(@ShellOption(help = "Author ID") Long id) {
        authorService.deleteAuthor(id);
        return String.format("Author with id %d deleted", id);
    }

    private String formatAuthor(Author author) {
        return String.format("ID: %d, Name: %s", author.getId(), author.getName());
    }
}
