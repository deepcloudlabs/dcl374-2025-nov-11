package com.example.lottery.service.business;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.lottery.service.LotteryService;
import com.example.lottery.service.RandomNumberService;

@Service
@RefreshScope
@ConditionalOnProperty(name = "lotteryType", havingValue = "simple")
@SuppressWarnings("unused")
public class SimpleLotteryService implements LotteryService {

	/*
	 * @Autowired
	 * 
	 * @ServiceQuality(ServiceQualityLevel.SECURE) private RandomNumberService
	 * randomNumberService;
	 * 
	 * 
	 * @Autowired
	 * 
	 * @ServiceQuality(ServiceQualityLevel.SECURE) public void
	 * setRandomNumberService(RandomNumberService randomNumberService) {
	 * this.randomNumberService = randomNumberService; }
	 */
	private final AtomicInteger counter = new AtomicInteger();
	private final List<RandomNumberService> randomNumberServices;
	private final Map<String, RandomNumberService> randomNumberServicesMap;
	private final int lotteryMin;
	private final int lotteryMax;
	private final int lotterySize;

	public SimpleLotteryService(List<RandomNumberService> randomNumberServices,
			Map<String, RandomNumberService> randomNumberServicesMap, @Value("${lotterySize}") int lotterySize,
			@Value("${lotteryMin}") int lotteryMin, @Value("${lotteryMax}") int lotteryMax) {
		this.randomNumberServices = randomNumberServices;
		this.randomNumberServicesMap = randomNumberServicesMap;
		this.lotteryMin = lotteryMin;
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
		System.err.println("Using %d,%d,%d".formatted(lotteryMin, lotteryMax, lotterySize));
		randomNumberServicesMap.forEach(
				(beanName, bean) -> System.out.println("%s: %s".formatted(beanName, bean.getClass().getName())));
	}

	@Override
	public List<Integer> draw() {
		var randomNumberService = randomNumberServices.get(counter.getAndIncrement() % randomNumberServices.size());
		return IntStream.generate(() -> randomNumberService.generate(lotteryMin, lotteryMax)).distinct()
				.limit(lotterySize).sorted().boxed().toList();
	}

}
