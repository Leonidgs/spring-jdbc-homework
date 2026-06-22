package ru.diasoft.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDao {

    @PersistenceContext
    private EntityManager em;

    public Genre insert(Genre genre) {
        em.persist(genre);
        return genre;
    }

    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    public void deleteById(Long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
    }

    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    public List<Genre> findAll() {
        return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
    }

    public boolean existsById(Long id) {
        Long count = em.createQuery("SELECT COUNT(g) FROM Genre g WHERE g.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
