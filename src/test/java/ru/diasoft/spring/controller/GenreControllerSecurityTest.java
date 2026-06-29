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
class GenreControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllGenres_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getGenreById_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createGenre_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateGenre_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(put("/api/genres/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteGenre_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllGenres_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getGenreById_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/genres/1"))
                .andExpect(status().isOk());
    }
}
