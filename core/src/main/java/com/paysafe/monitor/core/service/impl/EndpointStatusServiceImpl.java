package com.paysafe.monitor.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.model.ServerStatusReport;
import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;
import com.paysafe.monitor.core.repository.MerchantRepository;

@Service
public class EndpointStatusServiceImpl implements com.paysafe.monitor.core.service.EndpointStatusService {

	private static final Logger LOG = LoggerFactory.getLogger(EndpointStatusServiceImpl.class);
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private ExecutionHistoryRepository executionHistoryRepository;
	
	@Override
	public void verify() {
		LOG.info("Verifying endpoint status");
		ServerStatusReport report = merchantRepository.verify();
		executionHistoryRepository.updateLastExecution(report);
	}

	@Override
	public void configure(String hostname) {
		merchantRepository.updateHostName(hostname);
		
	}

}
