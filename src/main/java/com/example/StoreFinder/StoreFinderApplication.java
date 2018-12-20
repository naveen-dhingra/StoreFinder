package com.example.StoreFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example" )
public class StoreFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreFinderApplication.class, args);
	}
}

