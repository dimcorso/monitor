package com.paysafe.monitor.core.facade;

import com.paysafe.monitor.core.model.Config;

public interface MonitorFacade {

	void tick();
	
	void configure(Config config);
}
