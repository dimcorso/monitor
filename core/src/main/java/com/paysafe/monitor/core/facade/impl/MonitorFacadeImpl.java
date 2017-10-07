package com.paysafe.monitor.core.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.facade.MonitorFacade;
import com.paysafe.monitor.core.service.IntervalManagementService;

@Service("monitorFacade")
public class MonitorFacadeImpl implements MonitorFacade {

	@Autowired
	private IntervalManagementService intervalManagerService;
	
	@Override
	public void fire() {
		System.out.println("can fire");
		if (intervalManagerService.isTimeExpired()) {
			intervalManagerService.updateLastExecution(new Date());
			System.out.println("fired");
		} 
	}
}
