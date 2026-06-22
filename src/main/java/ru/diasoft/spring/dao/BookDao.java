package ru.diasoft.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDao {

    @PersistenceContext
    private EntityManager em;

    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    public Book update(Book book) {
        return em.merge(book);
    }

    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public List<Book> findByAuthorId(Long authorId) {
        return em.createQuery(
                "SELECT b FROM Book b WHERE b.author.id = :authorId", Book.class)
                .setParameter("authorId", authorId)
                .getResultList();
    }

    public List<Book> findByGenreId(Long genreId) {
        return em.createQuery(
                "SELECT b FROM Book b WHERE b.genre.id = :genreId", Book.class)
                .setParameter("genreId", genreId)
                .getResultList();
    }

    public boolean existsById(Long id) {
        Long count = em.createQuery("SELECT COUNT(b) FROM Book b WHERE b.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
