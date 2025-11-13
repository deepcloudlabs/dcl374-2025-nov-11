package com.example.lottery.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lottery.aop.Profile;
import com.example.lottery.model.LotteryModel;
import com.example.lottery.service.LotteryService;

@RestController
@RequestMapping("/numbers")
@CrossOrigin
public class LotteryRestController {
	private final LotteryService lotteryService;

	public LotteryRestController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@GetMapping(params = { "max", "size" })
	@Profile
	public LotteryModel getLotteryNumbers(@RequestParam int max, @RequestParam int size) {
		return lotteryService.draw(max, size);
	}
}
