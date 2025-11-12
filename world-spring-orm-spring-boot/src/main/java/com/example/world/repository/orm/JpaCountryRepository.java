package com.example.world.repository.orm;

import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.world.entity.Country;
import com.example.world.repository.CountryRepository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Repository
public class JpaCountryRepository implements CountryRepository {

	private final EntityManager entityManager;
	private final TransactionTemplate txTemplate;

	public JpaCountryRepository(EntityManager entityManager, TransactionTemplate txTemplate) {
		this.entityManager = entityManager;
		this.txTemplate = txTemplate;
	}

	public Optional<Country> findOne(String code) {
		return ofNullable(entityManager.find(Country.class, code));
	}

	public Collection<Country> findAll() {
		return entityManager.createNamedQuery("Country.findAll", Country.class).getResultList();
	}

	@Transactional
	public Country add(Country country) {
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		txTemplate.executeWithoutResult( status -> {
			try {
				String code = country.getKod();
				Optional<Country> found = findOne(code);
				if (found.isPresent())
					throw new IllegalArgumentException("Country exists!");
				entityManager.persist(country);				
			} catch (Exception e) {
				status.setRollbackOnly();
			}
		});
		return country;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Country update(Country country) {
		String code = country.getKod();
		Optional<Country> found = findOne(code);
		if (found.isPresent()) {
			Country c = found.get();
			c.setPopulation(country.getPopulation());
			c.setSurface(country.getSurface());
			entityManager.merge(c); // UPDATE ....
			entityManager.flush();
			return c;
		}
		throw new IllegalArgumentException("Country does not exist!");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Optional<Country> remove(String key) {
		var country = findOne(key);
		country.ifPresent(entityManager::remove);
		return country;
	}

	public Collection<Country> getByContinent(String continent) {
		System.err.println("getByContinent(%s)".formatted(continent));
		EntityGraph<?> graph = entityManager.getEntityGraph("Country.withCapitalAndCities");
		return entityManager.createNamedQuery("Country.findByContinent", Country.class)
				.setHint("jakarta.persistence.fetchgraph", graph)
				.setParameter("continent", continent)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getContinents() {
		return entityManager.createNativeQuery("select distinct continent from country").getResultList();
	}

}
