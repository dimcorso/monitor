package com.paysafe.monitor.core.repository;

import java.util.Date;

public interface ExecutionHistoryRepository {

	Date getLastExcecution();

	void updateLastExecution(Date date);

}
