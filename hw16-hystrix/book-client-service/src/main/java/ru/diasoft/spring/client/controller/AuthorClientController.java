package ru.diasoft.spring.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.NameRequest;
import ru.diasoft.spring.client.service.AuthorClientService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorClientController {

    private final AuthorClientService authorClientService;

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorClientService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        return authorClientService.getAuthorById(id);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody NameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorClientService.createAuthor(request));
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable Long id, @RequestBody NameRequest request) {
        return authorClientService.updateAuthor(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorClientService.deleteAuthor(id);
    }
}
