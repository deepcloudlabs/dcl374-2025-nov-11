package com.example.aspect;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProfileAspect implements Ordered{

	@Around("@annotion(profile) && execution(* com.example..*(..))")
	public Object profileMethod(ProceedingJoinPoint pjp,Profile profile) throws Throwable {
		var start = System.nanoTime();
		var result = pjp.proceed();
		var stop = System.nanoTime();
		var unit = profile.value();
		System.out.println("%s runs %d %s".formatted(pjp.getSignature().getName(),unit.convert(stop-start,TimeUnit.NANOSECONDS),unit.name()));
		return result;
	}
	
	@Around("@target(profile) && execution(* com.example..*(..))")
	public Object profileAllClassMethods(ProceedingJoinPoint pjp,Profile profile) throws Throwable {
		var start = System.nanoTime();
		var result = pjp.proceed();
		var stop = System.nanoTime();
		var unit = profile.value();
		System.out.println("%s runs %d %s".formatted(pjp.getSignature().getName(),unit.convert(stop-start,TimeUnit.NANOSECONDS),unit.name()));
		return result;
	}
//	@Before
//	@After
//	@AfterThrowing
//	@AfterReturning

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}
}
