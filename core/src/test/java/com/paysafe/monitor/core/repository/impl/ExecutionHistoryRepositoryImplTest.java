package com.paysafe.monitor.core.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.paysafe.monitor.core.model.ServerStatusReport;

public class ExecutionHistoryRepositoryImplTest {

	private ExecutionHistoryRepositoryImpl repo;
	
	@Before
	public void setUp() throws Exception {
		repo = new ExecutionHistoryRepositoryImpl();
	}

	@Test
	public void testGetLastExcecutionWhenNoHistory() throws Exception {
		Optional<Date> lastExcecution = repo.getLastExcecution();
		
		assertFalse(lastExcecution.isPresent());
	}
	
	@Test
	public void testGetLastExcecutionWhenHistory() throws Exception {
		ServerStatusReport report = new ServerStatusReport(true, new Date());
		repo.updateLastExecution(report);
		
		Optional<Date> lastExcecution = repo.getLastExcecution();
		
		assertEquals(report.getDate(),lastExcecution.get());
	}

	@Test
	public void testUpdateLastExecution() throws Exception {
		ServerStatusReport report = new ServerStatusReport(true, new Date());
		repo.updateLastExecution(report);
		
		report = new ServerStatusReport(false, new Date());
		repo.updateLastExecution(report);
		
		List<ServerStatusReport> history = repo.getHistory();
		
		assertEquals(2, history.size());
		assertTrue(history.get(0).isAvailable());
		assertFalse(history.get(1).isAvailable());
	}

}
