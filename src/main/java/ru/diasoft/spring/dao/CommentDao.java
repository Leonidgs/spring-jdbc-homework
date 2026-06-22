package ru.diasoft.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentDao {

    @PersistenceContext
    private EntityManager em;

    public Comment insert(Comment comment) {
        em.persist(comment);
        return comment;
    }

    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    public void deleteById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
    }

    public Optional<Comment> findById(Long id) {
        List<Comment> result = em.createQuery(
                "SELECT c FROM Comment c JOIN FETCH c.book WHERE c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<Comment> findByBookId(Long bookId) {
        return em.createQuery(
                "SELECT c FROM Comment c JOIN FETCH c.book WHERE c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
