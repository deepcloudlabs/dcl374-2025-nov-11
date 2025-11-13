package com.example.jmx.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.example.jmx.aop.ProfileAspect;

@Component
@ManagedResource(objectName = "com.deepcloudlabs:type=MethodStats", description = "Method statistics MBean")
public class MethodStats implements MethodStatsMBean {
	private final ProfileAspect profileAspect;

	public MethodStats(ProfileAspect profileAspect) {
		this.profileAspect = profileAspect;
	}

	@Override
    @ManagedAttribute(description = "Returns custom method metrics collected by ProfileAspect")
	public String[] getCustomMetrics() {
		return profileAspect.getCustomMetrics().stream()
		        .map(Object::toString)
		        .toArray(String[]::new);
	}

}
