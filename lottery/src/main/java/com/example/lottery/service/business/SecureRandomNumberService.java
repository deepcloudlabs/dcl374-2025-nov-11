package com.example.lottery.service.business;

import java.security.SecureRandom;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.lottery.service.RandomNumberService;
import com.example.lottery.service.ServiceQuality;
import com.example.lottery.service.ServiceQualityLevel;

@Service
@ServiceQuality(ServiceQualityLevel.SECURE)
@Profile({"prod","preprod"})
public class SecureRandomNumberService implements RandomNumberService {
	private SecureRandom random = new SecureRandom();

	@Override
	public int generate(int min, int max) {
		System.err.println("SecureRandomNumberService::generate");
		return min + random.nextInt(max-min+1);
	}

}
