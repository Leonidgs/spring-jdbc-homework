package ru.diasoft.spring.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.diasoft.spring.domain.Book;
import ru.diasoft.spring.kafka.dto.BookCreateRequestEvent;
import ru.diasoft.spring.kafka.dto.BookCreatedEvent;
import ru.diasoft.spring.service.BookService;

@Component
@Profile("!test")
@Slf4j
@RequiredArgsConstructor
public class BookRequestConsumer {

    private final BookService bookService;
    private final BookEventProducer bookEventProducer;

    @KafkaListener(topics = "${kafka.topics.book-create-requests}", groupId = "${spring.kafka.consumer.group-id}")
    public void onBookCreateRequest(BookCreateRequestEvent request) {
        log.info("Received BookCreateRequestEvent: {}", request);
        Long bookId = bookService.createBook(request.getTitle(), request.getAuthorId(), request.getGenreId());
        Book book = bookService.getBookById(bookId);
        BookCreatedEvent event = new BookCreatedEvent(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenre().getName()
        );
        bookEventProducer.publishBookCreated(event);
    }
}
