package com.example.actuator.service;

import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.example.actuator.aop.ProfileAspect;
import com.example.actuator.metrics.MethodMetrics;

@Component
@Endpoint(id = "methodmetrics") // exposed at /actuator/custommetrics
public class MethodMetricsEndpoint {
	private final ProfileAspect profileAspect;

	public MethodMetricsEndpoint(ProfileAspect profileAspect) {
		this.profileAspect = profileAspect;
	}

	@ReadOperation
	public List<Map<String, MethodMetrics>> getCustomMetrics() {
		return profileAspect.getCustomMetrics();
	};

}
