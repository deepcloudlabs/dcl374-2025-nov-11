package com.example.world.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.world.document.Country;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
public interface MongoCountryRepository extends MongoRepository<Country, String> {
	Slice<Country> getByContinent(Pageable page, String continent);
	List<Country> findByContinentAndPopulationBetween(String continent,long populationFrom,long populationTo);
	Collection<Country> findTop10ByOrderByPopulationDesc();
	Page<Country> findAll(Pageable page);
	@Query("{continent: ?1, population: {$gt: ?2, $lt: ?3}}")
	List<Country> kitaUlkeleriniGetir(Pageable page,String continent,long populationFrom,long populationTo);

}
