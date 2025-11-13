package com.example.jmx.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jmx.dto.response.LotteryDTO;
import com.example.jmx.service.LotteryService;

@RestController
@RequestMapping("/numbers")
public class SampleRestController {
	
	private final LotteryService lotteryService;

	public SampleRestController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@GetMapping(params= {"column"})
	public List<LotteryDTO> getNumbers(@RequestParam int column){
		return lotteryService.draw(column);
	}
}
