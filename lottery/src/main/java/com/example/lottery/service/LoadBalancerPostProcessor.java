package com.example.lottery.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LoadBalancerPostProcessor implements BeanPostProcessor {
	private final ConfigurableApplicationContext applicationContext;
	private final Map<Class<?>, AtomicInteger> nextComponent = new ConcurrentHashMap<>();

	public LoadBalancerPostProcessor(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		ReflectionUtils.doWithFields(bean.getClass(), field -> {
			var lb = field.getAnnotation(LoadBalance.class);
			if (lb != null) {
				Class<?> clazz = field.getType();
				System.out.println(clazz);
				var implementations = List.copyOf(applicationContext.getBeansOfType(clazz).values());
				System.out.println(implementations);
				nextComponent.putIfAbsent(clazz, new AtomicInteger(0));
				var counter = nextComponent.get(clazz);
				var implementation = implementations.get(counter.getAndIncrement() % implementations.size());
				field.setAccessible(true);
				ReflectionUtils.setField(field, bean,implementation);
				field.setAccessible(false);
			}
		});
		return bean;
	}
}
