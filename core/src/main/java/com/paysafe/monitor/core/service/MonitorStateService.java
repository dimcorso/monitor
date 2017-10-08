package com.paysafe.monitor.core.service;

public interface MonitorStateService {

	boolean isStarted();

	void configure(String state);

}