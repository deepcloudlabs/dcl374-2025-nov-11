package com.example.lottery.service.business;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.lottery.service.LotteryService;
import com.example.lottery.service.RandomNumberService;

@Service
public class SimpleLotteryService implements LotteryService {
	private final RandomNumberService randomNumberService;
	
	public SimpleLotteryService(RandomNumberService randomNumberService) {
		this.randomNumberService = randomNumberService;
	}

	@Override
	public List<Integer> draw() {	
		return IntStream.generate( () -> randomNumberService.generate(1,60))
				        .distinct()
				        .limit(6)
				        .sorted()
				        .boxed()
				        .toList();
	}

}
