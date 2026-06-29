package ru.diasoft.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.spring.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
