package com.paysafe.monitor.core.facade.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.facade.MonitorFacade;
import com.paysafe.monitor.core.model.Config;
import com.paysafe.monitor.core.model.ServerStatusSummary;
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
		if (config.getInterval() != null) {
			intervalManagerService.configure(config.getInterval());
		}
		
		if (config.getState() != null) {
			monitorStateService.configure(config.getState());
		}
		
		if (config.getHostname() != null) {
			endpointStatusService.configure(config.getHostname());
		}
		
		LOG.info("Configuration updated");
	}
	
	
	@Override
	public List<ServerStatusSummary> buildReport() {
		return endpointStatusService.buildReport();
	}
}
