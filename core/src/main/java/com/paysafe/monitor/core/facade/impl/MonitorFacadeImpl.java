package com.paysafe.monitor.core.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.facade.MonitorFacade;
import com.paysafe.monitor.core.model.Config;
import com.paysafe.monitor.core.service.IntervalManagementService;
import com.paysafe.monitor.core.service.MonitorStateService;

@Service("monitorFacade")
public class MonitorFacadeImpl implements MonitorFacade {

	@Autowired
	private IntervalManagementService intervalManagerService;
	
	@Autowired
	private MonitorStateService monitorStateService;
	
	@Override
	public void fire() {
		System.out.println("can fire");
		if (monitorStateService.isStarted() && intervalManagerService.isTimeExpired()) {
			intervalManagerService.updateLastExecution(new Date());
			System.out.println("fired");
		} 
	}

	@Override
	public void configure(Config config) {
		intervalManagerService.configure(config);
		monitorStateService.configure(config.getState());
		System.out.println("configuration updated");
	}
}
