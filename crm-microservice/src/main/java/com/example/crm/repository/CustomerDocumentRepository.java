package com.example.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.crm.document.CustomerDocument;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {
	List<CustomerDocument> findAllByAddressesCity(String city, Pageable page);

	Optional<CustomerDocument> findByEmail(String email);
}
