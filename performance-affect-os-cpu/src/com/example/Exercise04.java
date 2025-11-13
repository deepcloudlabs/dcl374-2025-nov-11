package com.example;

public class Exercise04 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int x = 42; // 4B -> Stack
		
		Integer y = Integer.valueOf(42); // reference variable
		// Stack: 8B -> UseCompressedOops: 4B
		// Heap: (Object Header) 12B + 4B = 16B
		// int -> Integer -> 20B
		// -XX:AutoBoxCacheMax=2048
		Integer a = 42; 
		Integer b = 42; 
		Integer u = 542; 
		Integer v = 542; 
		System.out.println("a==b? "+(a==b));
		System.out.println("u==v? "+(u==v));
	}

}
