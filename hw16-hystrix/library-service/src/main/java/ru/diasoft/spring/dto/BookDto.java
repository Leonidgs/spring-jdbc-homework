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

    public static BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(AuthorDto.from(book.getAuthor()))
                .genre(GenreDto.from(book.getGenre()))
                .build();
    }
}
