package ru.diasoft.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Команды Shell для работы с жанрами")
class GenreCommandsTest {

    @Autowired
    private GenreCommands genreCommands;

    @Test
    @DisplayName("должен отображать список жанров")
    void shouldListGenres() {
        String result = genreCommands.listGenres();

        assertThat(result).contains("Test Genre 1", "Test Genre 2");
    }

    @Test
    @DisplayName("должен находить жанр по id")
    void shouldGetGenreById() {
        String result = genreCommands.getGenre(1L);

        assertThat(result).contains("Test Genre 1");
    }

    @Test
    @DisplayName("должен создавать новый жанр")
    @DirtiesContext
    void shouldCreateGenre() {
        String result = genreCommands.createGenre("Shell Test Genre");

        assertThat(result).contains("Genre created with id:");
    }

    @Test
    @DisplayName("должен обновлять жанр")
    @DirtiesContext
    void shouldUpdateGenre() {
        String result = genreCommands.updateGenre(1L, "Updated by Shell");

        assertThat(result).contains("Genre with id 1 updated");
    }

    @Test
    @DisplayName("должен удалять жанр")
    @DirtiesContext
    void shouldDeleteGenre() {
        String result = genreCommands.deleteGenre(2L);

        assertThat(result).contains("Genre with id 2 deleted");
    }
}
