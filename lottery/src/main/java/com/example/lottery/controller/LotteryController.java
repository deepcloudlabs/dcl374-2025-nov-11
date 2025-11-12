package com.example.lottery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lottery.service.LotteryService;

@RestController
@RequestMapping("/numbers")
public class LotteryController {
	private final LotteryService lotteryService;

	public LotteryController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}
	
	@GetMapping(params= {"column"})
	public List<List<Integer>> getLotteryNumbers(@RequestParam int column){
		return lotteryService.draw(column);
	}
}
