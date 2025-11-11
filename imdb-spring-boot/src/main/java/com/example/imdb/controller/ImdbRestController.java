package com.example.imdb.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.imdb.entity.Movie;
import com.example.imdb.service.MovieService;
import com.example.imdb.service.WebConfig;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
// set PATH=c:\DEVEL\stage\opt\curl-7.45.0\bin;%PATH%
// curl "http://localhost:4400/imdb/api/v1/movies?fromYear=1970&toYear=1979"
@RestController
//@Scope("request")
@RequestScope
@RequestMapping("/movies")
@WebConfig
public class ImdbRestController {
	@Autowired
	private MovieService movieService;
	
	public ImdbRestController() {
		System.err.println("ImdbRestController()");
		System.err.println(movieService);
	}
	
	@PostConstruct
	public void baslangicIslemleri() {
		System.err.println("baslangicIslemleri()");
		System.err.println(movieService);
	}
	
	@PreDestroy
	public void sonlandirmaIslemleri() {
		System.err.println("sonlandirmaIslemleri()");
		System.err.println(movieService);
	}

	@GetMapping(params= {"fromYear","toYear"})
	public Collection<Movie> getMoviesByYearRange(
			@RequestParam(defaultValue = "1970") int fromYear,
			@RequestParam(defaultValue = "1980") int toYear){
		return movieService.findAllMoviesByYearRange(fromYear, toYear);
	}
}
