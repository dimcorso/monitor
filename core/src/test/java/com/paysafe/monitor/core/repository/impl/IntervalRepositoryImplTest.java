package com.paysafe.monitor.core.repository.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IntervalRepositoryImplTest {

	private IntervalRepositoryImpl repo;
	
	@Before
	public void setUp() throws Exception {
		repo = new IntervalRepositoryImpl();
	}

	@Test
	public void testRetrieveInterval() throws Exception {
		assertEquals(Integer.valueOf(10000), repo.retrieveInterval());
	}

	@Test
	public void testUpdateInterval() throws Exception {
		repo.updateInterval(3000);
		assertEquals(Integer.valueOf(3000), repo.retrieveInterval());
	}

}
