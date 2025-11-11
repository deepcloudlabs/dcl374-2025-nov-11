package com.example.lottery.service.business;

import java.util.SplittableRandom;

import com.example.lottery.service.RandomNumberService;

public class UglyRandomNumberService implements RandomNumberService {
	private SplittableRandom random = new SplittableRandom();

	@Override
	public int generate(int min, int max) {
		System.err.println("UglyRandomNumberService::generate");
		return random.nextInt();
	}

}
