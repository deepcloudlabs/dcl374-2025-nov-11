package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class Exercise07 {
	private static Map<String,Integer> numbers = new HashMap<>();
	private static Map<String,Integer> numbers2 = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		// Thread-safety + NOT multi-core efficient
		numbers = Collections.synchronizedMap(numbers);
        // ConcurrentHashMap: TS + multi-core efficient
		// 10 Regions
	}

}
