package com.paysafe.monitor.core.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;
import com.paysafe.monitor.core.repository.IntervalRepository;
import com.paysafe.monitor.core.service.IntervalManagementService;

@Service
public class IntervalManagementServiceImpl implements IntervalManagementService {

	@Autowired
	private IntervalRepository intervalRepository;
	
	@Autowired
	private ExecutionHistoryRepository executionHistoryRepository;
	
	@Override
	public boolean isTimeExpired() {
		Integer interval = intervalRepository.retrieveInterval();
		Optional<Date> lastExcecution = executionHistoryRepository.getLastExcecution();
		Date now = new Date();
		Long elapsedTime = now.getTime() - lastExcecution.orElseGet(() -> new Date(0)).getTime();
		return elapsedTime > interval;
	}

	@Override
	public void configure(Integer interval) {
		intervalRepository.updateInterval(interval);
	}

}
