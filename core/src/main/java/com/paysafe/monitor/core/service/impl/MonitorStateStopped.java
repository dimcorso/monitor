package com.paysafe.monitor.core.service.impl;

public class MonitorStateStopped extends MonitorState {

	@Override
	public boolean isStarted() {
		return false;
	}

}
