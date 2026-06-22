package ru.diasoft.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Author;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDao {

    @PersistenceContext
    private EntityManager em;

    public Author insert(Author author) {
        em.persist(author);
        return author;
    }

    public Author update(Author author) {
        return em.merge(author);
    }

    public void deleteById(Long id) {
        Author author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
        }
    }

    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    public boolean existsById(Long id) {
        Long count = em.createQuery("SELECT COUNT(a) FROM Author a WHERE a.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
