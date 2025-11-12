package com.example.world;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.world.repository.CountryRepository;

@SpringBootApplication
public class WorldJdbcClientApplication implements ApplicationRunner{
	private final CountryRepository countryRepository;
	
	public WorldJdbcClientApplication(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorldJdbcClientApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		countryRepository.getByContinent("Asia")
		                 .forEach(System.out::println);
	}

}
