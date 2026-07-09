package ru.diasoft.spring.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.BookRequest;

import java.util.List;

@FeignClient(name = "book-service-books", url = "${book-service.url}")
public interface BookFeignClient {

    @GetMapping("/api/books")
    List<BookDto> getAllBooks();

    @GetMapping("/api/books/{id}")
    BookDto getBookById(@PathVariable("id") Long id);

    @PostMapping("/api/books")
    BookDto createBook(@RequestBody BookRequest request);

    @PutMapping("/api/books/{id}")
    BookDto updateBook(@PathVariable("id") Long id, @RequestBody BookRequest request);

    @DeleteMapping("/api/books/{id}")
    void deleteBook(@PathVariable("id") Long id);
}
