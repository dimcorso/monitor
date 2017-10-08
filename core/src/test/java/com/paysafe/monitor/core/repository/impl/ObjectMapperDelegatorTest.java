package com.paysafe.monitor.core.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.paysafe.monitor.core.model.Config;

@RunWith(MockitoJUnitRunner.class)
public class ObjectMapperDelegatorTest {

	@Mock
	private com.fasterxml.jackson.databind.ObjectMapper mapper;
	
	@InjectMocks
	private ObjectMapperDelegator delegator = new ObjectMapperDelegator();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testReadValue() throws Exception {
		String json = "{}";
		doReturn(new Config()).when(mapper).readValue(json, Config.class);
		
		Config config = delegator.readValue(json, Config.class);
		
		assertTrue(config instanceof Config);
		verify(mapper).readValue(json, Config.class);
	}
	
	@Test(expected=RuntimeException.class)
	public void testReadValueWhenException() throws Exception {
		doThrow(new IOException()).when(mapper).readValue("{}", Config.class);
		delegator.readValue("{}", Config.class);
	}
	
	@Test(expected=RuntimeException.class)
	public void testWriteValueWhenException() throws Exception {
		doThrow(new JsonParseException(null,"")).when(mapper).writeValueAsString(any(Config.class));
		
		delegator.writeValue(new Config());
	}
	

	@Test
	public void testWriteValue() throws Exception {
		Config conf = new Config();
		doReturn("{}").when(mapper).writeValueAsString(conf);
		
		String json = delegator.writeValue(conf);
		
		assertEquals("{}", json);
		verify(mapper).writeValueAsString(conf);
	}

}
