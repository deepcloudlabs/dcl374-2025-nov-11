package com.example;

import java.util.List;

public class Exercise05 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		var points = List.of(
				new Point(1,2), // 12B (Object Header) + 4B (x) + 4B (y) + 4B (padding)= 24B
				new Point(3,4),
				new Point(5,6),
				new Point(7,8)
		);
       // Cache: 4B (x) | 4B (y) | 4B (x) | 4B (y) | ...
	   // 64B / 24B ~ 2 Point
	}

}

// Immutable -> Object Pooling/Caching
record Point(int x,int y) {} // 8B
