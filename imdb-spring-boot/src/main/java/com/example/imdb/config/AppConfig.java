package com.example.imdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.example.imdb.service.MovieService;
import com.example.imdb.service.SequenceService;
import com.example.imdb.service.business.InMemoryMovieService;

@Configuration
public class AppConfig {

	@Bean(initMethod = "populate")
	@Scope("singleton")
	MovieService createMovieService(SequenceService sequenceService) {
		return new InMemoryMovieService(sequenceService);
	}
}
