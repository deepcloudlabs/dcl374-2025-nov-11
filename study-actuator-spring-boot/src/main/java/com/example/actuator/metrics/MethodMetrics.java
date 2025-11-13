package com.example.actuator.metrics;

public record MethodMetrics(String className,String methodName,int counter,double averageTime,double totalTime) {

}
