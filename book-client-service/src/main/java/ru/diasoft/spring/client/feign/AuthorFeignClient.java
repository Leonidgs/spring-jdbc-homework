package ru.diasoft.spring.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.NameRequest;

import java.util.List;

@FeignClient(name = "book-service-authors", url = "${book-service.url}")
public interface AuthorFeignClient {

    @GetMapping("/api/authors")
    List<AuthorDto> getAllAuthors();

    @GetMapping("/api/authors/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id);

    @PostMapping("/api/authors")
    AuthorDto createAuthor(@RequestBody NameRequest request);

    @PutMapping("/api/authors/{id}")
    AuthorDto updateAuthor(@PathVariable("id") Long id, @RequestBody NameRequest request);

    @DeleteMapping("/api/authors/{id}")
    void deleteAuthor(@PathVariable("id") Long id);
}
