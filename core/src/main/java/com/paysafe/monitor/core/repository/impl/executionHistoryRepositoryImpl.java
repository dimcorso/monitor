package com.paysafe.monitor.core.repository.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;

@Repository
public class executionHistoryRepositoryImpl implements ExecutionHistoryRepository {

	private Date lastExcecution;
	
	public executionHistoryRepositoryImpl() {
		super();
		init();
	}
	
	@PostConstruct
	private void init() {
		lastExcecution = new Date();
	}
	
	@Override
	public Date getLastExcecution() {
		return lastExcecution;
	}

	@Override
	public synchronized void updateLastExecution(Date date) {
		
		lastExcecution = date;
	}

}
