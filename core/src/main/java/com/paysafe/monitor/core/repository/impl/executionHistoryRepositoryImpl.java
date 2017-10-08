package com.paysafe.monitor.core.repository.impl;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.paysafe.monitor.core.model.ServerStatusReport;
import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;

@Repository
public class ExecutionHistoryRepositoryImpl implements ExecutionHistoryRepository {

	private static final Logger LOG = LoggerFactory.getLogger(ExecutionHistoryRepositoryImpl.class);
	
	private List<ServerStatusReport> history;
	
	public ExecutionHistoryRepositoryImpl() {
		super();
		init();
	}
	
	@PostConstruct
	private void init() {
		history = Collections.synchronizedList(new LinkedList<>());
	}
	
	@Override
	public Optional<Date> getLastExcecution() {
		return history.isEmpty()? Optional.empty() : Optional.of(history.get(history.size() - 1).getDate());
	}

	@Override
	public void updateLastExecution(ServerStatusReport report) {
		LOG.info("Status: " + report.isAvailable());
		history.add(report);
	}
	
	@Override
	public List<ServerStatusReport> getHistory() {
		return history;
	}

}
