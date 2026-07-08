package ru.diasoft.spring.client.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreatedEvent {

    private Long id;
    private String title;
    private String authorName;
    private String genreName;
}
