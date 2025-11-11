package com.example.lottery.service.business;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.lottery.service.RandomNumberService;
import com.example.lottery.service.ServiceQuality;
import com.example.lottery.service.ServiceQualityLevel;

@Service
@ServiceQuality(ServiceQualityLevel.FAST)
@Profile({"dev","prod","preprod"})
public class FastRandomNumberService implements RandomNumberService {

	@Override
	public int generate(int min, int max) {
		System.err.println("FastRandomNumberService::generate");
		return ThreadLocalRandom.current().nextInt(min,max);
	}

}
