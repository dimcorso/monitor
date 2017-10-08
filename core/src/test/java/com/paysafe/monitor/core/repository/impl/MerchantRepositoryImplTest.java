package com.paysafe.monitor.core.repository.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.paysafe.monitor.core.model.EndpointStatus;
import com.paysafe.monitor.core.model.ServerStatusReport;

@RunWith(MockitoJUnitRunner.class)
public class MerchantRepositoryImplTest {

	@Spy
	private MerchantRepositoryImpl repo = new MerchantRepositoryImpl();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testVerifyWhenNoErrorAndAvailable() throws Exception {
		mockHttpResponse("READY");
		
		ServerStatusReport status = repo.verify();
		
		assertTrue(status.isAvailable());
		assertNotNull(status.getDate());
	}

	@Test
	public void testVerifyWhenNoErrorAndNotAvailable() throws Exception {
		mockHttpResponse("NOT READY");
		
		ServerStatusReport status = repo.verify();
		
		assertFalse(status.isAvailable());
		assertNotNull(status.getDate());
	}
	
	
	@Test
	public void testVerifyWhenError() throws Exception {

		doThrow(new UnirestException("")).when(repo).get(anyString(), eq(EndpointStatus.class));
		
		ServerStatusReport status = repo.verify();
		
		assertFalse(status.isAvailable());
		assertNotNull(status.getDate());
	}
	
	private void mockHttpResponse(String statusValue) throws UnirestException {
		repo.updateHostName("local");
		String url = "http://local/accountmanagement/monitor";
		HttpResponse<EndpointStatus> response = mock(HttpResponse.class);
		EndpointStatus endpointStatus = new EndpointStatus();
		endpointStatus.setStatus(statusValue);
		doReturn(endpointStatus).when(response).getBody();
		doReturn(response).when(repo).get(url, EndpointStatus.class);
	}
}
