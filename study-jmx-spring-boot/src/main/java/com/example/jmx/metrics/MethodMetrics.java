package com.example.jmx.metrics;

public record MethodMetrics(String className,String methodName,int counter,double averageTime,double totalTime) {

}
