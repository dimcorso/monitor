package com.paysafe.monitor.core.service;

import java.util.List;

import com.paysafe.monitor.core.model.ServerStatusSummary;

public interface EndpointStatusService {

	void verify();

	void configure(String hostname);

	List<ServerStatusSummary> buildReport();

}
