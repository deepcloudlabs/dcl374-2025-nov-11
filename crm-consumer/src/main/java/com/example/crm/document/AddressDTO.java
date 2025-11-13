package com.example.crm.document;

public record AddressDTO(
		AddressType type,
		String country,
		String city,
		 String line
		)
{}