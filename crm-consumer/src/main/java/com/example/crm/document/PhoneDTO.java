package com.example.crm.document;

public record PhoneDTO(
	PhoneType type,
	String countryCode,
	String number,
	String extension
) {}