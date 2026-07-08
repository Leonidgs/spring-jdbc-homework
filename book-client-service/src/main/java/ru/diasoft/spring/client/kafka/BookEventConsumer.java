package ru.diasoft.spring.client.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.client.kafka.dto.BookCreatedEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookEventConsumer {

    @CacheEvict(value = "books", allEntries = true)
    @KafkaListener(topics = "${kafka.topics.book-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void onBookCreated(BookCreatedEvent event) {
        log.info("Received BookCreatedEvent: id={}, title='{}', author='{}', genre='{}'",
                event.getId(), event.getTitle(), event.getAuthorName(), event.getGenreName());
    }
}
