package com.example.lottery.service.business;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.lottery.aop.Profile;
import com.example.lottery.model.LotteryModel;
import com.example.lottery.service.LotteryService;

@Service
public class StandardLotteryService implements LotteryService {

	@Override
	@Profile	
	public LotteryModel draw(int max, int size) {
		return new LotteryModel(ThreadLocalRandom.current().ints(1, max)
                .distinct()
                .limit(size)
                .sorted()
                .boxed() 
                .toList());
	}

}
