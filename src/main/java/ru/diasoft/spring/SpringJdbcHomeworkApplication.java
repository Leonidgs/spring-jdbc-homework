package ru.diasoft.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "ru.diasoft.spring.controller",
        "ru.diasoft.spring.domain",
        "ru.diasoft.spring.dto",
        "ru.diasoft.spring.repository",
        "ru.diasoft.spring.service",
        "ru.diasoft.spring.shell"
})
public class SpringJdbcHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcHomeworkApplication.class, args);
	}

}
