package ru.diasoft.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBooks_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getBookById_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createBook_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"authorId\":1,\"genreId\":1}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateBook_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"authorId\":1,\"genreId\":1}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteBook_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllBooks_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getBookById_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void createBook_authenticated_returnsCreated() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\",\"authorId\":1,\"genreId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user")
    void updateBook_authenticated_returnsOk() throws Exception {
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated\",\"authorId\":1,\"genreId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteBook_authenticated_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}
