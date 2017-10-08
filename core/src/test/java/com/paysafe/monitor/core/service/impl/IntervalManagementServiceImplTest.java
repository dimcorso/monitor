package com.paysafe.monitor.core.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;
import com.paysafe.monitor.core.repository.IntervalRepository;

@RunWith(MockitoJUnitRunner.class)
public class IntervalManagementServiceImplTest {
	
	private static final int TEST_INTERVAL = 1000;

	@Mock
	private ExecutionHistoryRepository executionHistoryRepository = mock(ExecutionHistoryRepository.class);

	@Mock
	private IntervalRepository intervalRepository = mock(IntervalRepository.class);
	
	@InjectMocks
	private IntervalManagementServiceImpl service;

	@Before
	public void setUp() throws Exception {
		doReturn(TEST_INTERVAL).when(intervalRepository).retrieveInterval();
		doReturn(Optional.of(new Date())).when(executionHistoryRepository).getLastExcecution();
	}

	@Test
	public void testIsTimeExpired() throws Exception {
		
		Thread.sleep(3000);
		
		assertTrue(service.isTimeExpired());

	}
	
	@Test
	public void testIsTimeNotExpired() throws Exception {
		
		Thread.sleep(100);
		
		assertFalse(service.isTimeExpired());

	}
	
	
	@Test
	public void testIsTimeExpiredWhenNoLastExecution() throws Exception {
		
		doReturn(Optional.empty()).when(executionHistoryRepository).getLastExcecution();
		assertTrue(service.isTimeExpired());

	}

	@Test
	public void testConfigure() throws Exception {
		service.configure(TEST_INTERVAL);
		
		verify(intervalRepository).updateInterval(TEST_INTERVAL);
	}

}
