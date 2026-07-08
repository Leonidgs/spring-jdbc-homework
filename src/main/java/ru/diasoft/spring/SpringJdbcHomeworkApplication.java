package ru.diasoft.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
		basePackages = "ru.diasoft.spring",
		excludeFilters = @ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "ru.diasoft.spring.client..*"
		)
)
public class SpringJdbcHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcHomeworkApplication.class, args);
	}

}
