package ru.diasoft.spring.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ActuatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthShouldReturnUpAndLibraryIndicator() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.library.status").value("UP"))
                .andExpect(jsonPath("$.components.library.details.books").exists());
    }

    @Test
    void libraryHealthIndicatorShouldReturnDetails() throws Exception {
        mockMvc.perform(get("/actuator/health/library"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.details.books").exists())
                .andExpect(jsonPath("$.details.message").value("Library has books"));
    }

    @Test
    void metricsEndpointShouldBeAvailable() throws Exception {
        mockMvc.perform(get("/actuator/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names").exists());
    }

    @Test
    void infoEndpointShouldBeAvailable() throws Exception {
        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk());
    }

    @Test
    void logfileEndpointShouldBeAvailable() throws Exception {
        mockMvc.perform(get("/actuator/logfile"))
                .andExpect(status().isOk());
    }

    @Test
    void dataRestBooksShouldReturnHalResponse() throws Exception {
        mockMvc.perform(get("/datarest/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void dataRestAuthorsShouldReturnHalResponse() throws Exception {
        mockMvc.perform(get("/datarest/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    void dataRestGenresShouldReturnHalResponse() throws Exception {
        mockMvc.perform(get("/datarest/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links").exists());
    }
}
