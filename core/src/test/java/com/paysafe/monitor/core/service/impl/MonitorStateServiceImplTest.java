package com.paysafe.monitor.core.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

public class MonitorStateServiceImplTest {

	private MonitorStateServiceImpl service;

	@Before
	public void setUp() throws Exception {
		
		Map<String, Supplier<MonitorState>> stateMap = new HashMap<>();
		stateMap.put("started",MonitorStateStarted::new);
		stateMap.put("stopped",MonitorStateStopped::new);
		
		this.service = new MonitorStateServiceImpl(stateMap);
	}

	@Test
	public void testIsStartedWhenStopped() throws Exception {
		
		assertFalse(service.isStarted());
	}
	
	@Test
	public void testIsStartedWhenStarted() throws Exception {
		service.configure("started");
		
		assertTrue(service.isStarted());
	}

	@Test(expected=InvalidParameterException.class)
	public void testConfigureWhenInvalidState() throws Exception {
		service.configure("paused");
	}

}
