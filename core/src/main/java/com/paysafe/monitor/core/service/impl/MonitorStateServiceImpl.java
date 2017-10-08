package com.paysafe.monitor.core.service.impl;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.paysafe.monitor.core.service.MonitorStateService;

public class MonitorStateServiceImpl implements MonitorStateService {

	private Map<String, Supplier<MonitorState>> stateMap = new HashMap<>();
	
	private MonitorState monitorState = new MonitorStateStopped();

	
	public MonitorStateServiceImpl(Map<String, Supplier<MonitorState>> stateMap) {
		super();
		this.stateMap = stateMap;
	}

	@Override
	public boolean isStarted() {
		return monitorState.isStarted();
	}

	@Override
	public void configure(String state) {
		if (stateMap.containsKey(state)) {
			monitorState = stateMap.get(state).get();
		} else {
			throw new InvalidParameterException("Provided state is not valid: " + state);
		}
	}

}
