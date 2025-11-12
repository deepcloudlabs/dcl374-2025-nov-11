package com.example.world.controller;

import java.util.Collection;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@RestController
@RequestScope
@RequestMapping("/countries")
@CrossOrigin
public class CountryRestController {
	private final CountryRepository countryRepository;

	public CountryRestController(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	// http://localhost:9001/world/api/countries/TUR
	@GetMapping("{code}")
	public Country getByCode(@PathVariable String code) {
		return countryRepository.findOneByKod(code);
	}

	// http://localhost:9001/world/api/countries
	// ?continent=Asia&size=2&no=11
	// http://localhost:9100/world/api/countries?continent=Asia
	@GetMapping(params= {"continent"})
	public Collection<Country> getAllByContinent(@RequestParam String continent) {
		return countryRepository.findByContinent(PageRequest.of(0, 20),continent).getContent();
	}

	@PostMapping
	public Country addCountry(@RequestBody Country country) {
		countryRepository.save(country);
		return country;
	}
}
