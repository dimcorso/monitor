package com.paysafe.monitor.core.repository.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.paysafe.monitor.core.repository.IntervalRepository;

@Repository
public class IntervalRepositoryImpl implements IntervalRepository {

	private Integer interval;
	
	public IntervalRepositoryImpl() {
		init();
	}
	
	@PostConstruct
	private void init() {
		interval = 10000;
	}
	
	@Override
	public Integer retrieveInterval() {
		return interval;
	}

	@Override
	public void updateInterval(int interval) {
		this.interval = interval;
	}

}
