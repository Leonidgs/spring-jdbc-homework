
package ru.diasoft.spring.client.service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.diasoft.spring.client.dto.AuthorDto;
import ru.diasoft.spring.client.dto.BookDto;
import ru.diasoft.spring.client.dto.BookRequest;
import ru.diasoft.spring.client.dto.CommentDto;
import ru.diasoft.spring.client.dto.CommentRequest;
import ru.diasoft.spring.client.dto.GenreDto;
import ru.diasoft.spring.client.dto.NameRequest;
import ru.diasoft.spring.client.feign.AuthorFeignClient;
import ru.diasoft.spring.client.feign.BookFeignClient;
import ru.diasoft.spring.client.feign.CommentFeignClient;
import ru.diasoft.spring.client.feign.GenreFeignClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ClientServiceCircuitBreakerTest {

    private static final Long ID = 1L;
    private static final String FALLBACK_NAME = "N/A — book-service unavailable";
    private static final String FALLBACK_TEXT = "N/A — book-service unavailable";

    @Autowired
    private AuthorClientService authorClientService;
    @Autowired
    private BookClientService bookClientService;
    @Autowired
    private CommentClientService commentClientService;
    @Autowired
    private GenreClientService genreClientService;

    @MockBean
    private AuthorFeignClient authorFeignClient;
    @MockBean
    private BookFeignClient bookFeignClient;
    @MockBean
    private CommentFeignClient commentFeignClient;
    @MockBean
    private GenreFeignClient genreFeignClient;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @BeforeEach
    void resetCircuitBreaker() {
        circuitBreakerRegistry.circuitBreaker("bookService").reset();
    }

    @Test
    void authorGetAllShouldReturnEmptyFallback() {
        when(authorFeignClient.getAllAuthors()).thenThrow(new RuntimeException("fail"));

        List<AuthorDto> result = authorClientService.getAllAuthors();

        assertThat(result).isEmpty();
        verify(authorFeignClient).getAllAuthors();
    }

    @Test
    void authorGetByIdShouldReturnStubFallback() {
        when(authorFeignClient.getAuthorById(ID)).thenThrow(new RuntimeException("fail"));

        AuthorDto result = authorClientService.getAuthorById(ID);

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void authorCreateShouldReturnStubFallback() {
        when(authorFeignClient.createAuthor(new NameRequest("test"))).thenThrow(new RuntimeException("fail"));

        AuthorDto result = authorClientService.createAuthor(new NameRequest("test"));

        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void authorUpdateShouldReturnStubFallback() {
        when(authorFeignClient.updateAuthor(ID, new NameRequest("test"))).thenThrow(new RuntimeException("fail"));

        AuthorDto result = authorClientService.updateAuthor(ID, new NameRequest("test"));

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void authorDeleteShouldReturnVoidFallback() {
        doThrow(new RuntimeException("fail")).when(authorFeignClient).deleteAuthor(ID);

        authorClientService.deleteAuthor(ID);

        verify(authorFeignClient).deleteAuthor(ID);
    }

    @Test
    void bookGetAllShouldReturnEmptyFallback() {
        when(bookFeignClient.getAllBooks()).thenThrow(new RuntimeException("fail"));

        List<BookDto> result = bookClientService.getAllBooks();

        assertThat(result).isEmpty();
        verify(bookFeignClient).getAllBooks();
    }

    @Test
    void bookGetByIdShouldReturnStubFallback() {
        when(bookFeignClient.getBookById(ID)).thenThrow(new RuntimeException("fail"));

        BookDto result = bookClientService.getBookById(ID);

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getTitle()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void bookCreateShouldReturnStubFallback() {
        when(bookFeignClient.createBook(new BookRequest())).thenThrow(new RuntimeException("fail"));

        BookDto result = bookClientService.createBook(new BookRequest());

        assertThat(result.getTitle()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void bookUpdateShouldReturnStubFallback() {
        when(bookFeignClient.updateBook(ID, new BookRequest())).thenThrow(new RuntimeException("fail"));

        BookDto result = bookClientService.updateBook(ID, new BookRequest());

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getTitle()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void bookDeleteShouldReturnVoidFallback() {
        doThrow(new RuntimeException("fail")).when(bookFeignClient).deleteBook(ID);

        bookClientService.deleteBook(ID);

        verify(bookFeignClient).deleteBook(ID);
    }

    @Test
    void commentGetByBookShouldReturnEmptyFallback() {
        when(commentFeignClient.getCommentsByBook(ID)).thenThrow(new RuntimeException("fail"));

        List<CommentDto> result = commentClientService.getCommentsByBook(ID);

        assertThat(result).isEmpty();
        verify(commentFeignClient).getCommentsByBook(ID);
    }

    @Test
    void commentGetByIdShouldReturnStubFallback() {
        when(commentFeignClient.getCommentById(ID)).thenThrow(new RuntimeException("fail"));

        CommentDto result = commentClientService.getCommentById(ID);

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getText()).isEqualTo(FALLBACK_TEXT);
    }

    @Test
    void commentCreateShouldReturnStubFallback() {
        when(commentFeignClient.createComment(ID, new CommentRequest("test"))).thenThrow(new RuntimeException("fail"));

        CommentDto result = commentClientService.createComment(ID, new CommentRequest("test"));

        assertThat(result.getBookId()).isEqualTo(ID);
        assertThat(result.getText()).isEqualTo(FALLBACK_TEXT);
    }

    @Test
    void commentUpdateShouldReturnStubFallback() {
        when(commentFeignClient.updateComment(ID, new CommentRequest("test"))).thenThrow(new RuntimeException("fail"));

        CommentDto result = commentClientService.updateComment(ID, new CommentRequest("test"));

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getText()).isEqualTo(FALLBACK_TEXT);
    }

    @Test
    void commentDeleteShouldReturnVoidFallback() {
        doThrow(new RuntimeException("fail")).when(commentFeignClient).deleteComment(ID);

        commentClientService.deleteComment(ID);

        verify(commentFeignClient).deleteComment(ID);
    }

    @Test
    void genreGetAllShouldReturnEmptyFallback() {
        when(genreFeignClient.getAllGenres()).thenThrow(new RuntimeException("fail"));

        List<GenreDto> result = genreClientService.getAllGenres();

        assertThat(result).isEmpty();
        verify(genreFeignClient).getAllGenres();
    }

    @Test
    void genreGetByIdShouldReturnStubFallback() {
        when(genreFeignClient.getGenreById(ID)).thenThrow(new RuntimeException("fail"));

        GenreDto result = genreClientService.getGenreById(ID);

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void genreCreateShouldReturnStubFallback() {
        when(genreFeignClient.createGenre(new NameRequest("test"))).thenThrow(new RuntimeException("fail"));

        GenreDto result = genreClientService.createGenre(new NameRequest("test"));

        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void genreUpdateShouldReturnStubFallback() {
        when(genreFeignClient.updateGenre(ID, new NameRequest("test"))).thenThrow(new RuntimeException("fail"));

        GenreDto result = genreClientService.updateGenre(ID, new NameRequest("test"));

        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getName()).isEqualTo(FALLBACK_NAME);
    }

    @Test
    void genreDeleteShouldReturnVoidFallback() {
        doThrow(new RuntimeException("fail")).when(genreFeignClient).deleteGenre(ID);

        genreClientService.deleteGenre(ID);

        verify(genreFeignClient).deleteGenre(ID);
    }
}

