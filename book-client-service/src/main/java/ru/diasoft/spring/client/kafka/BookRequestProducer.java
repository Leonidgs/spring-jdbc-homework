package ru.diasoft.spring.client.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.client.kafka.dto.BookCreateRequestEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookRequestProducer {

    private final KafkaTemplate<String, BookCreateRequestEvent> kafkaTemplate;

    @Value("${kafka.topics.book-create-requests}")
    private String bookCreateRequestsTopic;

    public void sendCreateBookRequest(String title, Long authorId, Long genreId) {
        BookCreateRequestEvent event = new BookCreateRequestEvent(title, authorId, genreId);
        log.info("Sending BookCreateRequestEvent to topic '{}': {}", bookCreateRequestsTopic, event);
        kafkaTemplate.send(bookCreateRequestsTopic, event);
    }
}
