package com.example.aspect;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Component
@Aspect
public class AuditAspect {


	@Around("execution(* com.example..*(..))")
	public Object audit(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		Date now = new Date();
		System.out.println(String.format("%s runs at %s", methodName, now));
		System.out.println("Parameters are " + Arrays.toString(pjp.getArgs()));
		Object result = pjp.proceed();
		System.out.println(String.format("%s returns %s", methodName, result));
		return result;
	}

}
