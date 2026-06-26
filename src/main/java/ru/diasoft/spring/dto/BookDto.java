package ru.diasoft.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.domain.Book;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;

    public static BookDto fromDomain(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(AuthorDto.fromDomain(book.getAuthor()))
                .genre(GenreDto.fromDomain(book.getGenre()))
                .build();
    }
}
