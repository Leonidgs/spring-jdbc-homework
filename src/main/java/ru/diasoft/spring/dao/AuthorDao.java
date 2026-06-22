package ru.diasoft.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Author;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> Author.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .build();

    public Long insert(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (:name)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(Author author) {
        String sql = "UPDATE authors SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        jdbcTemplate.update(sql, params);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM authors WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    public Optional<Author> findById(Long id) {
        String sql = "SELECT id, name FROM authors WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        List<Author> authors = jdbcTemplate.query(sql, params, authorRowMapper);
        return authors.isEmpty() ? Optional.empty() : Optional.of(authors.get(0));
    }

    public List<Author> findAll() {
        String sql = "SELECT id, name FROM authors";
        return jdbcTemplate.query(sql, authorRowMapper);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM authors WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
