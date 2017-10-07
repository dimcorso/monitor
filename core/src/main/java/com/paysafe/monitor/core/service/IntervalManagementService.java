package com.paysafe.monitor.core.service;

import java.util.Date;

import com.paysafe.monitor.core.model.Config;

public interface IntervalManagementService {

	boolean isTimeExpired();

	void updateLastExecution(Date date);

	void configure(Config config);

}
