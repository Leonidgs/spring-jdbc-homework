package ru.diasoft.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreDao.class)
@ActiveProfiles("test")
@DisplayName("DAO для работы с жанрами")
class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("должен находить жанр по id")
    void shouldFindGenreById() {
        Optional<Genre> genre = genreDao.findById(1L);

        assertThat(genre).isPresent();
        assertThat(genre.get().getId()).isEqualTo(1L);
        assertThat(genre.get().getName()).isEqualTo("Test Genre 1");
    }

    @Test
    @DisplayName("должен возвращать empty optional если жанр не найден")
    void shouldReturnEmptyIfGenreNotFound() {
        Optional<Genre> genre = genreDao.findById(999L);

        assertThat(genre).isEmpty();
    }

    @Test
    @DisplayName("должен возвращать все жанры")
    void shouldReturnAllGenres() {
        List<Genre> genres = genreDao.findAll();

        assertThat(genres).hasSize(2);
        assertThat(genres).extracting(Genre::getName)
                .containsExactlyInAnyOrder("Test Genre 1", "Test Genre 2");
    }

    @Test
    @DisplayName("должен сохранять новый жанр")
    void shouldSaveNewGenre() {
        Genre newGenre = Genre.builder()
                .name("New Genre")
                .build();

        Genre saved = genreDao.insert(newGenre);

        assertThat(saved.getId()).isNotNull();
        Optional<Genre> savedGenre = genreDao.findById(saved.getId());
        assertThat(savedGenre).isPresent();
        assertThat(savedGenre.get().getName()).isEqualTo("New Genre");
    }

    @Test
    @DisplayName("должен обновлять жанр")
    void shouldUpdateGenre() {
        Genre genre = genreDao.findById(1L).orElseThrow();
        genre.setName("Updated Genre");

        genreDao.update(genre);

        Optional<Genre> updatedGenre = genreDao.findById(1L);
        assertThat(updatedGenre).isPresent();
        assertThat(updatedGenre.get().getName()).isEqualTo("Updated Genre");
    }

    @Test
    @DisplayName("должен удалять жанр")
    void shouldDeleteGenre() {
        genreDao.deleteById(2L);

        Optional<Genre> deletedGenre = genreDao.findById(2L);
        assertThat(deletedGenre).isEmpty();
    }

    @Test
    @DisplayName("должен проверять существование жанра")
    void shouldCheckGenreExists() {
        assertThat(genreDao.existsById(1L)).isTrue();
        assertThat(genreDao.existsById(999L)).isFalse();
    }
}
