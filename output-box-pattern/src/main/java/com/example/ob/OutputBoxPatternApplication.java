package com.example.ob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutputBoxPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutputBoxPatternApplication.class, args);
	}

}
