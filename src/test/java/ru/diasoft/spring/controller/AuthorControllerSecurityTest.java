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
class AuthorControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllAuthors_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getAuthorById_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createAuthor_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateAuthor_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteAuthor_unauthenticated_returnsRedirect() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllAuthors_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void getAuthorById_authenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk());
    }
}
