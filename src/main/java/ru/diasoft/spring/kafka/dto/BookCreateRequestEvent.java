package ru.diasoft.spring.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequestEvent {

    private String title;
    private Long authorId;
    private Long genreId;
}
