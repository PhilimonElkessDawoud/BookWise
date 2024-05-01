package com.bookwise.bookwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookwiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookwiseApplication.class, args);
	}

}
