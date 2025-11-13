package com.example.jmx.aop;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.jmx.metrics.MethodMetrics;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Component
@Aspect
public class ProfileAspect {
	private static final Map<String, MethodMetrics> methodMetrics = new ConcurrentHashMap<>();

	@Around("@annotation(profile) && execution(* com.example..*(..))")
	public Object profileMethod(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		return profile(pjp, profile);
	}

	@Around("@target(profile) && execution(* com.example..*(..))")
	public Object profileAllClassMethods(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		return profile(pjp, profile);
	}

	public Object profile(ProceedingJoinPoint pjp, Profile profile) throws Throwable {
		var methodName = pjp.getSignature().getName();
		var className = pjp.getTarget().getClass().getName();
		var methodMetric = methodMetrics.computeIfAbsent(methodName,
				_ -> new MethodMetrics(className, methodName, 0, 0, 0));
		var start = System.nanoTime();
		var result = pjp.proceed();
		var stop = System.nanoTime();
		var duration = stop - start;
		var counter = methodMetric.counter() + 1;
		var totalTime = methodMetric.totalTime() + duration;
		var averageTime = totalTime / counter;
		methodMetrics.put(methodName, new MethodMetrics(className, methodName, counter, averageTime, totalTime));
		return result;
	}
	
	public List<Map<String, MethodMetrics>> getCustomMetrics() {
		return methodMetrics.entrySet().stream().map(entry -> Map.of(entry.getKey(), entry.getValue())).toList();
	};
}
