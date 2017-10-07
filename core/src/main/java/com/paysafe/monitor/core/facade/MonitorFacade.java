package com.paysafe.monitor.core.facade;

import com.paysafe.monitor.core.model.Config;

public interface MonitorFacade {

	void fire();
	
	void configure(Config config);
}
