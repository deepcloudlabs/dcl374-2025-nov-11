package com.example.crm.dto.response;

import com.example.crm.document.PhoneType;

public record Phone(
		PhoneType type,
		String countryCode,
		String number,
		String extension
) 
{}
