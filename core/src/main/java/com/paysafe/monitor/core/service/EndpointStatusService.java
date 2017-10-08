package com.paysafe.monitor.core.service;

import java.util.LinkedList;

import com.paysafe.monitor.core.model.ServerStatusSummary;

public interface EndpointStatusService {

	void verify();

	void configure(String hostname);

	LinkedList<ServerStatusSummary> buildReport();

}
