package com.paysafe.monitor.core.service;

import java.util.Date;

public interface IntervalManagementService {

	boolean isTimeExpired();

	void updateLastExecution(Date date);

}
