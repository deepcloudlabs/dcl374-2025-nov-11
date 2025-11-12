package com.example.service.business;

import org.springframework.stereotype.Service;

import com.example.aspect.Profile;
import com.example.service.Calculator;

@Service
@Profile
public class StandardCalculator implements Calculator {

	@Override
	public double add(double x, double y) {
		return x + y;
	}

	@Override
	public double sub(double x, double y) {
		return x - y;
	}

	@Override
	public double mul(double x, double y) {
		return x * y;
	}

	@Override
	public double div(double x, double y) {
		return x / y;
	}

}
