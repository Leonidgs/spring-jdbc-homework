package ru.diasoft.spring.client.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topics.book-create-requests}")
    private String bookCreateRequestsTopic;

    @Value("${kafka.topics.book-events}")
    private String bookEventsTopic;

    @Bean
    public NewTopic bookCreateRequestsTopic() {
        return TopicBuilder.name(bookCreateRequestsTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic bookEventsTopic() {
        return TopicBuilder.name(bookEventsTopic).partitions(1).replicas(1).build();
    }
}
