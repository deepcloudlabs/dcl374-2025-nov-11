package com.example.crm.dto.response;

import java.util.List;


public record CustomerQLResponse(
		String identity,
		String fullName,
		String email,
		String iban,
		List<Address> addresses,
		List<Phone> phones
) 
{}
