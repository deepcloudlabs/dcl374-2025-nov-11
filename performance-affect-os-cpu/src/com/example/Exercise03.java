package com.example;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Exercise03 {
	private final static int ARR_SIZE = 2 * 1024 * 1024;
	// Heap: (Object Header) 12B + 4B + 2M * 4B = 16B + 8MB | 
	private static int[] testData = new int[ARR_SIZE];
	private static List<Integer> testData2 = new ArrayList<>(ARR_SIZE);
	
	public static void main(String[] args) {
		// Memory Footprint
		// Java's Array
		// 12B + 4B + 2M * 4B = 16B + 8MB
		// uses Cache effectively!
		// Cache Word: 64B -> 16 ints 
		
		// ArrayList ?
        // 2M -> Integer -> 20B -> 40MB
		// Cache Word: 64B -> 16B -> 4 int
	}

}
