package ru.diasoft.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorDao.class)
@ActiveProfiles("test")
@DisplayName("DAO для работы с авторами")
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("должен находить автора по id")
    void shouldFindAuthorById() {
        Optional<Author> author = authorDao.findById(1L);

        assertThat(author).isPresent();
        assertThat(author.get().getId()).isEqualTo(1L);
        assertThat(author.get().getName()).isEqualTo("Test Author 1");
    }

    @Test
    @DisplayName("должен возвращать empty optional если автор не найден")
    void shouldReturnEmptyIfAuthorNotFound() {
        Optional<Author> author = authorDao.findById(999L);

        assertThat(author).isEmpty();
    }

    @Test
    @DisplayName("должен возвращать всех авторов")
    void shouldReturnAllAuthors() {
        List<Author> authors = authorDao.findAll();

        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName)
                .containsExactlyInAnyOrder("Test Author 1", "Test Author 2");
    }

    @Test
    @DisplayName("должен сохранять нового автора")
    void shouldSaveNewAuthor() {
        Author newAuthor = Author.builder()
                .name("New Author")
                .build();

        Author saved = authorDao.insert(newAuthor);

        assertThat(saved.getId()).isNotNull();
        Optional<Author> found = authorDao.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("New Author");
    }

    @Test
    @DisplayName("должен обновлять автора")
    void shouldUpdateAuthor() {
        Author author = authorDao.findById(1L).orElseThrow();
        author.setName("Updated Author");

        authorDao.update(author);

        Optional<Author> updatedAuthor = authorDao.findById(1L);
        assertThat(updatedAuthor).isPresent();
        assertThat(updatedAuthor.get().getName()).isEqualTo("Updated Author");
    }

    @Test
    @DisplayName("должен удалять автора")
    void shouldDeleteAuthor() {
        authorDao.deleteById(2L);

        Optional<Author> deletedAuthor = authorDao.findById(2L);
        assertThat(deletedAuthor).isEmpty();
    }

    @Test
    @DisplayName("должен проверять существование автора")
    void shouldCheckAuthorExists() {
        assertThat(authorDao.existsById(1L)).isTrue();
        assertThat(authorDao.existsById(999L)).isFalse();
    }
}
