package com.example.lottery.aop;

import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.lottery.jfr.events.LotteryEvent;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Component
@Aspect
public class ProfileAspect {
	private static final AtomicLong counter = new AtomicLong(0);

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
		var start = System.nanoTime();
		var event = new LotteryEvent();
		event.begin();
		var result = pjp.proceed();
		var stop = System.nanoTime();
		var duration = stop - start;
		event.sequenceId = counter.getAndIncrement();
		event.interval = duration;
		event.className = className;
		event.methodName = methodName;
		event.end();
		event.commit();
		return result;
	}

}
