package ru.diasoft.spring.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.dto.NameRequest;

import java.util.List;

@FeignClient(name = "book-service-genres", url = "${book-service.url}")
public interface GenreFeignClient {

    @GetMapping("/api/genres")
    List<GenreDto> getAllGenres();

    @GetMapping("/api/genres/{id}")
    GenreDto getGenreById(@PathVariable("id") Long id);

    @PostMapping("/api/genres")
    GenreDto createGenre(@RequestBody NameRequest request);

    @PutMapping("/api/genres/{id}")
    GenreDto updateGenre(@PathVariable("id") Long id, @RequestBody NameRequest request);

    @DeleteMapping("/api/genres/{id}")
    void deleteGenre(@PathVariable("id") Long id);
}
