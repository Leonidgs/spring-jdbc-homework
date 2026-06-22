package ru.diasoft.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Book;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> Book.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .authorId(rs.getLong("author_id"))
            .genreId(rs.getLong("genre_id"))
            .build();

    public Long insert(Book book) {
        String sql = "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :authorId, :genreId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthorId())
                .addValue("genreId", book.getGenreId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(Book book) {
        String sql = "UPDATE books SET title = :title, author_id = :authorId, genre_id = :genreId WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthorId())
                .addValue("genreId", book.getGenreId());
        jdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, author_id, genre_id FROM books WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        List<Book> books = jdbcTemplate.query(sql, params, bookRowMapper);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    public List<Book> findAll() {
        String sql = "SELECT id, title, author_id, genre_id FROM books";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    public List<Book> findByAuthorId(Long authorId) {
        String sql = "SELECT id, title, author_id, genre_id FROM books WHERE author_id = :authorId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("authorId", authorId);
        return jdbcTemplate.query(sql, params, bookRowMapper);
    }

    public List<Book> findByGenreId(Long genreId) {
        String sql = "SELECT id, title, author_id, genre_id FROM books WHERE genre_id = :genreId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("genreId", genreId);
        return jdbcTemplate.query(sql, params, bookRowMapper);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM books WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
