package com.paysafe.monitor.core.facade;

import java.util.List;

import com.paysafe.monitor.core.model.Config;
import com.paysafe.monitor.core.model.ServerStatusSummary;

public interface MonitorFacade {

	void tick();
	
	void configure(Config config);

	List<ServerStatusSummary> buildReport();
}
