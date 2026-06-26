package ru.diasoft.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.domain.Genre;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

    private Long id;
    private String name;

    public static GenreDto fromDomain(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
