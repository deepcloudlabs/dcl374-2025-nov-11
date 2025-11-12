package com.example.world.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

@RestController
@RequestMapping("/countries")
@RequestScope
@CrossOrigin
public class WorldRestController {
	private final CountryRepository countryRepository;
	
	public WorldRestController(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@GetMapping(params= {"continent"})
	public Collection<Country> getCountries(@RequestParam String continent){
		System.err.println("getCountries(%s)".formatted(continent));
		return countryRepository.getByContinent(continent);
	}
}
