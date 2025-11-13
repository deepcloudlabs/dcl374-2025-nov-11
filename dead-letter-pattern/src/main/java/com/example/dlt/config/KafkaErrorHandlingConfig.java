package com.example.dlt.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaErrorHandlingConfig {

	@Bean
	DefaultErrorHandler defaultErrorHandler(KafkaTemplate<String, String> template) {
		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template,
				(record, ex) -> new TopicPartition(record.topic() + ".DLT", record.partition()));

		var backoff = new ExponentialBackOff();
		backoff.setInitialInterval(500);
		backoff.setMultiplier(2.0);
		backoff.setMaxInterval(5000);
		backoff.setMaxAttempts(3);

		DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backoff);
		handler.addNotRetryableExceptions(IllegalArgumentException.class);
		return handler;
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
			ConsumerFactory<String, String> cf, DefaultErrorHandler errorHandler) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(cf);
		factory.setCommonErrorHandler(errorHandler);
		factory.setConcurrency(3); 
		return factory;
	}
}