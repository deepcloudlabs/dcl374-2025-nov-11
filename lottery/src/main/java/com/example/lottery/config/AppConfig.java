package com.example.lottery.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.example.lottery.service.business.UglyRandomNumberService;

@Configuration
@Import(AppConfig.class)
public class AppConfig implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(
				UglyRandomNumberService.class);
		builder.setScope("request");
		registry.registerBeanDefinition("uglyRandomNumberService", builder.getBeanDefinition());
	}

}
