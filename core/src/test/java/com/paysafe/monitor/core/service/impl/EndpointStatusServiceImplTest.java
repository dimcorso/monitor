package com.paysafe.monitor.core.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.paysafe.monitor.core.model.ServerStatusReport;
import com.paysafe.monitor.core.model.ServerStatusSummary;
import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;
import com.paysafe.monitor.core.repository.MerchantRepository;

@RunWith(MockitoJUnitRunner.class)
public class EndpointStatusServiceImplTest {
	@Mock
	private ExecutionHistoryRepository executionHistoryRepository;

	@Mock
	private MerchantRepository merchantRepository;
	@InjectMocks
	private EndpointStatusServiceImpl service;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerify() throws Exception {
		ServerStatusReport report = new ServerStatusReport(true, new Date());
		doReturn(report).when(merchantRepository).verify();
		
		service.verify();
		
		verify(executionHistoryRepository).updateLastExecution(report);
	}

	@Test
	public void testConfigure() throws Exception {

		String hostname = "localhost";
		
		service.configure(hostname);
		
		verify(merchantRepository).updateHostName(hostname);
	}

	@Test
	public void testBuildReport() throws Exception {
		
		List<ServerStatusReport> history = Arrays.asList(
				//these 2 entries will be combined into one 
				new ServerStatusReport(true, new Date(0)),
				new ServerStatusReport(true, new Date(1000)),
				//these 2 entries will be combined into another one
				new ServerStatusReport(false, new Date(4000)), //this is in purpose, here we test the sorting
				new ServerStatusReport(false, new Date(2000))
			);
		
		doReturn(history).when(executionHistoryRepository).getHistory();
		
		List<ServerStatusSummary> report = service.buildReport();
		
		//4 entries in the history have been reduced to 2 ranges
		assertEquals(2, report.size());
		assertEquals(2000, report.get(0).getTo().getTime() - report.get(0).getFrom().getTime());
		
		assertEquals(2000, report.get(1).getTo().getTime() - report.get(1).getFrom().getTime());
		
	}

}
