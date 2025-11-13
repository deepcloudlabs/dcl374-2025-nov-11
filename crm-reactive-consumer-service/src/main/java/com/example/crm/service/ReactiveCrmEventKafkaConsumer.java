package com.example.crm.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class ReactiveCrmEventKafkaConsumer {
	 @Value("${kafka.bootstrap-servers}")
	    private String bootstrapServers;

	    @Value("${kafka.topic}")
	    private String topic;

	    @PostConstruct
	    public void consumeMessages() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, "reactive-consumer-group");
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

	        ReceiverOptions<String, String> options = ReceiverOptions.<String, String>create(props)
	                .subscription(java.util.Collections.singleton(topic));

	        Flux<ReceiverRecord<String, String>> kafkaFlux = KafkaReceiver.create(options).receive();

	        kafkaFlux
	            .doOnNext(record -> {
	                System.out.println("Received message: " + record.value());
	                record.receiverOffset().acknowledge();
	            })
	            .doOnError(e -> System.err.println("Kafka error: " + e.getMessage()))
	            .subscribe();
	    }
	}
