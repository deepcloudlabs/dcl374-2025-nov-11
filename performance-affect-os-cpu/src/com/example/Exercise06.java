package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exercise06 {
	private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(16);
	public static int fun() {
		try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
		return 42;
	}

	public static CompletableFuture<Integer> gun() {
		return CompletableFuture.supplyAsync(() -> {
			try {TimeUnit.SECONDS.sleep(5);}catch (Exception e) {}
			return 42;			
		}, EXECUTOR_SERVICE);
	}
	
	public static void main(String[] args) {
		System.out.println("[%s] Application is just started.".formatted(Thread.currentThread().getName()));
		var result = fun();
		System.out.println("[%s] result is %d".formatted(Thread.currentThread().getName(),result));
		                      // observer
		gun().thenAcceptAsync(response -> {
			System.out.println("[%s] response is %d".formatted(Thread.currentThread().getName(),response));			
		},EXECUTOR_SERVICE);
		for (var i=0;i<100;++i) {
			try {TimeUnit.MILLISECONDS.sleep(250);}catch (Exception e) {}
			System.out.println("[%s] Working hard: %d".formatted(Thread.currentThread().getName(),i));
		}
		System.out.println("[%s] Application is just been completed.".formatted(Thread.currentThread().getName()));
	}

}
