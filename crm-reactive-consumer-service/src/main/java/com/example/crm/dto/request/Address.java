package com.example.crm.dto.request;

public record Address(
		String country,String city,String line,
		String zipCode,AddressType addressType) {

}
