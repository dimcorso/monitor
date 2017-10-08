package com.paysafe.monitor.core.facade.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.paysafe.monitor.core.model.Config;
import com.paysafe.monitor.core.model.ServerStatusSummary;
import com.paysafe.monitor.core.service.EndpointStatusService;
import com.paysafe.monitor.core.service.IntervalManagementService;
import com.paysafe.monitor.core.service.MonitorStateService;

@RunWith(MockitoJUnitRunner.class)
public class MonitorFacadeImplTest {
	@Mock
	private EndpointStatusService endpointStatusService;

	@Mock
	private IntervalManagementService intervalManagerService;

	@Mock
	private MonitorStateService monitorStateService;
	@InjectMocks
	private MonitorFacadeImpl facade;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTickWhenNoStartedAndIntervalExpired() throws Exception {
		
		doReturn(false).when(monitorStateService).isStarted();
		doReturn(true).when(intervalManagerService).isTimeExpired();
		
		facade.tick();
		
		verify(endpointStatusService,never()).verify();
		
	}
	
	@Test
	public void testTickWhenNoStartedAndIntervalNotExpired() throws Exception {
		
		doReturn(false).when(monitorStateService).isStarted();
		doReturn(true).when(intervalManagerService).isTimeExpired();
		
		facade.tick();
		
		verify(endpointStatusService,never()).verify();
		
	}
	
	@Test
	public void testTickWhenStartedAndIntervalExpired() throws Exception {
		
		doReturn(true).when(monitorStateService).isStarted();
		doReturn(true).when(intervalManagerService).isTimeExpired();
		
		facade.tick();
		
		verify(endpointStatusService).verify();
		
	}
	
	@Test
	public void testTickWhenStartedAndIntervalNotExpired() throws Exception {
		
		doReturn(true).when(monitorStateService).isStarted();
		doReturn(false).when(intervalManagerService).isTimeExpired();
		
		facade.tick();
		
		verify(endpointStatusService,never()).verify();
		
	}

	@Test
	public void testConfigure() throws Exception {
		
		Config config = new Config();
		config.setHostname("local");
		config.setInterval(1000);
		config.setState("stated");
		
		facade.configure(config);
		
		verify(intervalManagerService).configure(config.getInterval());
		verify(monitorStateService).configure(config.getState());
		verify(endpointStatusService).configure(config.getHostname());
	}

	@Test
	public void testBuildReport() throws Exception {
		List<ServerStatusSummary> report = new ArrayList<>();
		doReturn(report).when(endpointStatusService).buildReport();
		
		List<ServerStatusSummary> result = facade.buildReport();
		
		assertEquals(report, result);
		
		
	}

}
