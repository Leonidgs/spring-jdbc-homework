package ru.diasoft.spring.client.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.service.BookServiceClient;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookClientController.class)
class BookClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookServiceClient bookServiceClient;

    @Test
    void getAllBooks_returnsBooks() throws Exception {
        BookDto book = new BookDto(1L, "War and Peace",
                new AuthorDto(1L, "Tolstoy"),
                new GenreDto(1L, "Novel"));
        when(bookServiceClient.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/client/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("War and Peace"))
                .andExpect(jsonPath("$[0].author.name").value("Tolstoy"));
    }

    @Test
    void getBookById_returnsBook() throws Exception {
        BookDto book = new BookDto(1L, "War and Peace",
                new AuthorDto(1L, "Tolstoy"),
                new GenreDto(1L, "Novel"));
        when(bookServiceClient.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/client/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("War and Peace"));
    }

    @Test
    void getAllAuthors_returnsAuthors() throws Exception {
        when(bookServiceClient.getAllAuthors()).thenReturn(List.of(new AuthorDto(1L, "Tolstoy")));

        mockMvc.perform(get("/api/client/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tolstoy"));
    }

    @Test
    void getAllGenres_returnsGenres() throws Exception {
        when(bookServiceClient.getAllGenres()).thenReturn(List.of(new GenreDto(1L, "Novel")));

        mockMvc.perform(get("/api/client/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Novel"));
    }
}
