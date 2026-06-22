package ru.diasoft.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.dao.AuthorDao;
import ru.diasoft.spring.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDao authorDao;

    @Transactional
    public Long createAuthor(String name) {
        Author author = Author.builder()
                .name(name)
                .build();
        return authorDao.insert(author);
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Transactional
    public void updateAuthor(Long id, String name) {
        Author author = Author.builder()
                .id(id)
                .name(name)
                .build();
        authorDao.update(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorDao.deleteById(id);
    }
}
