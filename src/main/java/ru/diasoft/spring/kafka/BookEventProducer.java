package ru.diasoft.spring.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.kafka.dto.BookCreatedEvent;

@Component
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
public class BookEventProducer {

    private final KafkaTemplate<String, BookCreatedEvent> kafkaTemplate;

    @Value("${kafka.topics.book-events}")
    private String bookEventsTopic;

    public void publishBookCreated(BookCreatedEvent event) {
        log.info("Publishing BookCreatedEvent to topic '{}': {}", bookEventsTopic, event);
        kafkaTemplate.send(bookEventsTopic, event);
    }
}
