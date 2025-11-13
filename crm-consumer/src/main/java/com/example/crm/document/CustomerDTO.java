package com.example.crm.document;

import java.util.List;

public record CustomerDTO(
		String identityNo,
		String fullName,
		String email,
		List<AddressDTO> addresses,
		List<PhoneDTO> phones,
		String iban
		) {}