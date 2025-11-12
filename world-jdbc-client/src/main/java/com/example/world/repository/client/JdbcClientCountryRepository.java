package com.example.world.repository.client;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.world.entity.City;
import com.example.world.entity.Country;
import com.example.world.exception.EntityNotFoundException;
import com.example.world.repository.CityRepository;
import com.example.world.repository.CountryRepository;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Repository
public class JdbcClientCountryRepository implements CountryRepository {
	private static final String SELECT_COUNTRY_BY_CODE = "SELECT * FROM COUNTRY WHERE CODE=:code";
	private static final String SELECT_COUNTRIES_BY_CONTINENT = "SELECT * FROM COUNTRY WHERE CONTINENT=:continent";
	private static final String SELECT_ALL_COUNTRIES = "SELECT * FROM COUNTRY";
	private static final String INSERT_COUNTRY = 
			"""
			INSERT INTO COUNTRY(CODE,NAME,CONTINENT,POPULATION,SURFACEAREA) 
			VALUES(:code,:name,:continent,:population,:area)
			""";
	private static final String UPDATE_COUNTRY = 
			"""
            UPDATE COUNTRY SET POPULATION=:population, SURFACEAREA=:area
            WHERE CODE=:code
            """;
	private static final String DELETE_COUNTRY = "DELETE FROM COUNTRY WHERE CODE=:code";

	private final JdbcClient jdbcClient;
	private final CityRepository cityRepository;

	public JdbcClientCountryRepository(JdbcClient jdbcClient, CityRepository cityRepository) {
		this.jdbcClient = jdbcClient;
		this.cityRepository = cityRepository;
	}

	public Optional<Country> findOne(String code) {
		Country country = jdbcClient.sql(SELECT_COUNTRY_BY_CODE)
				                    .query(Country.class)
				                    .single();
		Collection<City> cities = cityRepository.findByCountryCode(code);
		cities.forEach(city -> city.setCountry(country));
		country.setCities(cities);
		return Optional.ofNullable(country);
	}

	public Collection<Country> findAll() {
		List<Country> countries = jdbcClient.sql(SELECT_ALL_COUNTRIES)
				                            .query(Country.class)
				                            .list();
		countries.forEach(country -> country.setCities(cityRepository.findByCountryCode(country.getCode())));
		return countries;
	}

	@Transactional
	public Country add(Country country) {
		int rowsAffected = jdbcClient.sql(INSERT_COUNTRY)
				                     .param("code",country.getCode())
				                     .param("name",country.getName())
				                     .param("continent",country.getContinent())
				                     .param("population",country.getPopulation())
				                     .param("area",country.getSurfaceArea())
				                     .update();
		System.err.println(String.format("%d rows affected.", rowsAffected));
		return country;
	}

	@Transactional
	public Country update(Country country) {
		String code = country.getCode();
		Optional<Country> existing = findOne(code);
		if (!existing.isPresent())
			throw new EntityNotFoundException("Country does not exist");
		jdbcClient.sql(UPDATE_COUNTRY)
		          .param("population",country.getPopulation())
		          .param("area",country.getSurfaceArea())
		          .param("code",country.getCode())
		          .update();
		return country;
	}

	@Transactional
	public Optional<Country> remove(String code) {
		Optional<Country> country = findOne(code);
		if (country.isPresent()) {
			jdbcClient.sql(DELETE_COUNTRY).param("code",code).update();
		}
		return country;
	}

	public Collection<Country> getByContinent(String continent) {
		return jdbcClient.sql(SELECT_COUNTRIES_BY_CONTINENT).param("continent",continent).query(Country.class).list();
	}

	@Override
	public Collection<String> getContinents() {
		return jdbcClient.sql("SELECT DISTINCT CONTINENT FROM COUNTRY").query(String.class).list();
	}

}
