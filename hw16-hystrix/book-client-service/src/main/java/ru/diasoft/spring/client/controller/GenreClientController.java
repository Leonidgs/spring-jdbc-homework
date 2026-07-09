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
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.dto.NameRequest;
import ru.diasoft.spring.client.service.GenreClientService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreClientController {

    private final GenreClientService genreClientService;

    @GetMapping
    public List<GenreDto> getAllGenres() {
        return genreClientService.getAllGenres();
    }

    @GetMapping("/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreClientService.getGenreById(id);
    }

    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody NameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreClientService.createGenre(request));
    }

    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable Long id, @RequestBody NameRequest request) {
        return genreClientService.updateGenre(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        genreClientService.deleteGenre(id);
    }
}
