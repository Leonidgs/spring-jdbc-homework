
package ru.diasoft.spring.client.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import ru.diasoft.spring.client.dto.BookDto;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircuitBreakerIntegrationTest {

    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(WireMockConfiguration.options().dynamicPort());

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        WIRE_MOCK_SERVER.start();
        registry.add("book-service.url", WIRE_MOCK_SERVER::baseUrl);
    }

    @AfterAll
    static void stopWireMock() {
        WIRE_MOCK_SERVER.stop();
    }

    @Autowired
    private BookClientService bookClientService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    void reset() {
        WIRE_MOCK_SERVER.resetAll();
        circuitBreakerRegistry.circuitBreaker("bookService").reset();
    }

    @Test
    void shouldOpenCircuitBreakerWhenRemoteServiceReturnsError() {
        WIRE_MOCK_SERVER.stubFor(get(urlEqualTo("/api/books"))
                .willReturn(aResponse().withStatus(500).withBody("Internal Server Error")));

        for (int i = 0; i < 5; i++) {
            try {
                bookClientService.getAllBooks();
            } catch (Exception e) {
                // Expected exception before circuit breaker opens
            }
        }

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("bookService");
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);

        List<BookDto> fallbackResult = bookClientService.getAllBooks();
        assertThat(fallbackResult).isEmpty();
    }
}

