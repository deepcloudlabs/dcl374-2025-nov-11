package com.example.lottery.service.business;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.example.lottery.service.LoadBalance;
import com.example.lottery.service.LotteryService;
import com.example.lottery.service.RandomNumberService;

@Service
@ConditionalOnProperty(name = "lotteryType", havingValue = "complex")
@RequestScope
public class ComplexLotteryService implements LotteryService {
	@LoadBalance
	private RandomNumberService randomNumberService;
	
	private final int lotteryMin;
	private final int lotteryMax;
	private final int lotterySize;
	
	public ComplexLotteryService(
			@Value("${lotterySize}") int lotterySize, 
			@Value("${lotteryMin}") int lotteryMin, 
			@Value("${lotteryMax}") int lotteryMax) {
		this.lotteryMin = lotteryMin;
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
	}

	@Override
	public List<Integer> draw() {
		return IntStream.generate(() -> randomNumberService.generate(lotteryMin, lotteryMax)).distinct().limit(lotterySize).sorted().boxed()
				.toList();
	}

}
