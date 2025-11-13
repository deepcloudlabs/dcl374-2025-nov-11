package com.example.crm.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
public class AppConfig {

	@Bean("events-executor")
	ExecutorService createExecutorService() {
		return Executors.newFixedThreadPool(20);
	}
}
