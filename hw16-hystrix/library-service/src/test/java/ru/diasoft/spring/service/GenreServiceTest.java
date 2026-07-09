package ru.diasoft.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Сервис для работы с жанрами")
class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("должен создавать новый жанр")
    @DirtiesContext
    void shouldCreateGenre() {
        Long id = genreService.createGenre("New Service Genre");

        assertThat(id).isNotNull();
        Genre created = genreService.getGenreById(id);
        assertThat(created.getName()).isEqualTo("New Service Genre");
    }

    @Test
    @DisplayName("должен находить жанр по id")
    void shouldGetGenreById() {
        Genre genre = genreService.getGenreById(1L);

        assertThat(genre.getId()).isEqualTo(1L);
        assertThat(genre.getName()).isEqualTo("Test Genre 1");
    }

    @Test
    @DisplayName("должен выбрасывать исключение если жанр не найден")
    void shouldThrowIfGenreNotFound() {
        assertThatThrownBy(() -> genreService.getGenreById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Genre not found");
    }

    @Test
    @DisplayName("должен возвращать все жанры")
    void shouldGetAllGenres() {
        List<Genre> genres = genreService.getAllGenres();

        assertThat(genres).hasSize(2);
    }

    @Test
    @DisplayName("должен обновлять жанр")
    @DirtiesContext
    void shouldUpdateGenre() {
        genreService.updateGenre(1L, "Updated Genre Name");

        Genre updated = genreService.getGenreById(1L);
        assertThat(updated.getName()).isEqualTo("Updated Genre Name");
    }

    @Test
    @DisplayName("должен удалять жанр")
    @DirtiesContext
    void shouldDeleteGenre() {
        genreService.deleteGenre(2L);

        assertThatThrownBy(() -> genreService.getGenreById(2L))
                .isInstanceOf(RuntimeException.class);
    }
}
