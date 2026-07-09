package ru.diasoft.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.domain.Genre;
import ru.diasoft.spring.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreService genreService;

    @ShellMethod(value = "Create a new genre", key = "genre-create")
    public String createGenre(@ShellOption(value = {"-n", "--name"}, help = "Genre name") String name) {
        Long genreId = genreService.createGenre(name);
        return String.format("Genre created with id: %d", genreId);
    }

    @ShellMethod(value = "Get genre by id", key = "genre-get")
    public String getGenre(@ShellOption(help = "Genre ID") Long id) {
        Genre genre = genreService.getGenreById(id);
        return formatGenre(genre);
    }

    @ShellMethod(value = "List all genres", key = "genre-list")
    public String listGenres() {
        List<Genre> genres = genreService.getAllGenres();
        if (genres.isEmpty()) {
            return "No genres found";
        }
        StringBuilder sb = new StringBuilder("Genres:\n");
        for (Genre genre : genres) {
            sb.append(formatGenre(genre)).append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Update genre", key = "genre-update")
    public String updateGenre(
            @ShellOption(help = "Genre ID") Long id,
            @ShellOption(value = {"-n", "--name"}, help = "Genre name") String name) {
        genreService.updateGenre(id, name);
        return String.format("Genre with id %d updated", id);
    }

    @ShellMethod(value = "Delete genre", key = "genre-delete")
    public String deleteGenre(@ShellOption(help = "Genre ID") Long id) {
        genreService.deleteGenre(id);
        return String.format("Genre with id %d deleted", id);
    }

    private String formatGenre(Genre genre) {
        return String.format("ID: %d, Name: %s", genre.getId(), genre.getName());
    }
}
