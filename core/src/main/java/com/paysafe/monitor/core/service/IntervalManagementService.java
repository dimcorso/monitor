package com.paysafe.monitor.core.service;

public interface IntervalManagementService {

	boolean isTimeExpired();

	void configure(Integer interval);

}
