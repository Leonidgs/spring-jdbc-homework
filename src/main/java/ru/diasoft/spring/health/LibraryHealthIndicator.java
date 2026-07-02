package ru.diasoft.spring.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        long bookCount = bookRepository.count();
        if (bookCount > 0) {
            return Health.up()
                    .withDetail("books", bookCount)
                    .withDetail("message", "Library has books")
                    .build();
        } else {
            return Health.down()
                    .withDetail("books", bookCount)
                    .withDetail("message", "Library is empty")
                    .build();
        }
    }
}
