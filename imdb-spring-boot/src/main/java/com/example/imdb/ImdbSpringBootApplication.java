package com.example.imdb;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.imdb.entity.Movie;
import com.example.imdb.service.MovieService;

@SpringBootApplication
public class ImdbSpringBootApplication implements ApplicationRunner {
	private final MovieService movieService;
	
	public ImdbSpringBootApplication(MovieService movieService) {
		this.movieService = movieService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ImdbSpringBootApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		movieService.findAllMoviesByYearRange(1970, 1979)
		            .stream()
		            .map(Movie::getTitle)
		            .forEach(System.out::println);
		
	}

}
