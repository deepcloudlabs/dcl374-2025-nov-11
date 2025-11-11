package com.example.lottery.service.business;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.lottery.service.RandomNumberService;
import com.example.lottery.service.ServiceQuality;
import com.example.lottery.service.ServiceQualityLevel;

@Service
@ServiceQuality(ServiceQualityLevel.DUMMY)
@Profile({"test","dev"})
public class DummyRandomNumberService implements RandomNumberService {
	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public int generate(int min, int max) {
		System.err.println("DummyRandomNumberService::generate");
		return min + counter.incrementAndGet() % (max - min + 1);
	}

}
