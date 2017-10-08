package com.paysafe.monitor.core.service;

public interface EndpointStatusService {

	void verify();

	void configure(String hostname);

}
