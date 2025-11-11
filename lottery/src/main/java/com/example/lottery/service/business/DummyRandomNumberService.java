package com.example.lottery.service.business;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.example.lottery.service.RandomNumberService;

@Service
public class DummyRandomNumberService implements RandomNumberService {
	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public int generate(int min, int max) {
		return min + counter.incrementAndGet() % (max - min + 1);
	}

}
