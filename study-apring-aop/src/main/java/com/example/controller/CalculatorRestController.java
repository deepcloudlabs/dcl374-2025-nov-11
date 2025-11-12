package com.example.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aspect.Profile;
import com.example.dto.request.CalculatorRequest;
import com.example.dto.response.CalculatorResponse;
import com.example.service.Calculator;

@RestController
@RequestMapping("/calculate")
@Profile(TimeUnit.MICROSECONDS)
public class CalculatorRestController {
	private final Calculator calculator;

	public CalculatorRestController(Calculator calculator) {
		this.calculator = calculator;
		System.err.println(this.calculator.getClass());
	}

	@PostMapping
	@Cacheable(cacheNames = "calculator-results")
	public CalculatorResponse calculate(@RequestBody CalculatorRequest request) {
		System.err.println("calculate(@RequestBody CalculatorRequest request)");
		var result = switch (request.opCode()) {
		case "+" -> {
			yield calculator.add(request.x(), request.y());
		}
		case "-" -> {
			yield calculator.sub(request.x(), request.y());
		}
		case "*" -> {
			yield calculator.mul(request.x(), request.y());
		}
		case "/" -> {
			yield calculator.div(request.x(), request.y());
		}
		default -> {
			throw new IllegalArgumentException("Invalid opCode: %s".formatted(request.opCode()));
		}
		};
		return new CalculatorResponse("Ok", result);
	}
}
