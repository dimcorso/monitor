package com.paysafe.monitor.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.paysafe.monitor.core.service.MonitorStateService;
import com.paysafe.monitor.core.service.impl.MonitorState;
import com.paysafe.monitor.core.service.impl.MonitorStateServiceImpl;
import com.paysafe.monitor.core.service.impl.MonitorStateStarted;
import com.paysafe.monitor.core.service.impl.MonitorStateStopped;

@Configuration
@ComponentScan("com.paysafe.monitor.core")
public class CoreConfig {

	@Bean
	public MonitorStateService monitorStateService() {
		
		Map<String, Supplier<MonitorState>> stateMap = new HashMap<>();
		stateMap.put("started",MonitorStateStarted::new);
		stateMap.put("stopped",MonitorStateStopped::new);
		
		return new MonitorStateServiceImpl(stateMap);
	}
	
}
