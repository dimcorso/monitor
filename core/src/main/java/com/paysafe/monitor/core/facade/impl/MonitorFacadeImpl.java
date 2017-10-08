package com.paysafe.monitor.core.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.facade.MonitorFacade;
import com.paysafe.monitor.core.model.Config;
import com.paysafe.monitor.core.service.EndpointStatusService;
import com.paysafe.monitor.core.service.IntervalManagementService;
import com.paysafe.monitor.core.service.MonitorStateService;

@Service("monitorFacade")
public class MonitorFacadeImpl implements MonitorFacade {

	private static final Logger LOG = LoggerFactory.getLogger(MonitorFacadeImpl.class);
	
	@Autowired
	private IntervalManagementService intervalManagerService;
	
	@Autowired
	private EndpointStatusService endpointStatusService;

	@Autowired
	private MonitorStateService monitorStateService;
	
	@Override
	public void tick() {
		LOG.info("monitor running...");
		if (monitorStateService.isStarted() && intervalManagerService.isTimeExpired()) {
			endpointStatusService.verify();
		} 
	}

	@Override
	public void configure(Config config) {
		intervalManagerService.configure(config.getInterval());
		monitorStateService.configure(config.getState());
		endpointStatusService.configure(config.getHostname());
		LOG.info("Configuration updated");
	}
}
