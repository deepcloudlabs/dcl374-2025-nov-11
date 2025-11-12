package com.example.world.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.world.repository.CountryRepository;

@Service
public class WorldService {

	private final CountryRepository countryRepository;

	public WorldService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	@Transactional(propagation = Propagation.NEVER)
	public void increasePopulation(int increment,String continent) {
		// JPA -> Bulk -> Transaction -> commit
		countryRepository.bulGetir(continent) // PersistenceContext
		                  .stream()
		                  .filter(country -> country.getPopulation() < 100_000_000)
		                 .forEach(country -> country.setPopulation(country.getPopulation()+increment));
	}
}
