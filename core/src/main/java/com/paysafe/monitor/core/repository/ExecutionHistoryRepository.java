package com.paysafe.monitor.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.paysafe.monitor.core.model.ServerStatusReport;

public interface ExecutionHistoryRepository {

	Optional<Date> getLastExcecution();

	void updateLastExecution(ServerStatusReport report);

	List<ServerStatusReport> getHistory();

}
