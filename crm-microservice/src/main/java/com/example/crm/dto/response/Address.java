package com.example.crm.dto.response;

import com.example.crm.document.AddressType;

public record Address(
		AddressType type,
		String country,
		String city,
		String line		
) 
{}
