package com.example;

public class Exercise02 {
	private final int ARR_SIZE = 2 * 1024 * 1024;
	// Heap: (Object Header) 12B + 4B + 2M * 4B = 16B + 8MB | 
	private volatile int[] testData = new int[ARR_SIZE]; 

	private void run() {
		System.err.println("Start: " + System.currentTimeMillis());
		for (int i = 0; i < 15_000; i++) {
			touchEveryLine();
			touchEveryItem();
		}
		System.err.println("Warmup finished: " + System.currentTimeMillis());
		System.err.println("Item,Line");

		for (int i = 0; i < 100; i++) {
			long t0 = System.nanoTime();
			touchEveryLine();
			long t1 = System.nanoTime();
			touchEveryItem();
			long t2 = System.nanoTime();
			long elItem = t2 - t1;
			long elLine = t1 - t0;
			System.err.println("%d,%d".formatted(elItem,elLine));
		}
	}

	private void touchEveryItem() {
		for (int i = 0; i < testData.length; i++)
			testData[i]++; // 2M
	}

	private void touchEveryLine() {
		for (int i = 0; i < testData.length; i += 16)
			testData[i]++; //  128K
	}

	public static void main(String[] args) {
		var c = new Exercise02();
		c.run();
	}
}
