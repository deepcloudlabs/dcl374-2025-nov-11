package com.example.jmx.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.jmx.aop.Profile;
import com.example.jmx.dto.response.LotteryDTO;

@Service
@Profile
public class LotteryService {

	public List<LotteryDTO> draw(final int column) {
		return IntStream.range(0, column).mapToObj(_ -> draw()).toList();
	}

	public LotteryDTO draw() {
		return new LotteryDTO(IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1, 60)).distinct().limit(6)
				.sorted().boxed().toList());
	}

}
