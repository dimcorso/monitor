package com.paysafe.monitor.core.repository.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.paysafe.monitor.core.model.EndpointStatus;
import com.paysafe.monitor.core.model.ServerStatusReport;
import com.paysafe.monitor.core.repository.MerchantRepository;

@Repository
public class MerchantRepositoryImpl implements MerchantRepository {
 

	private static final Logger LOG = LoggerFactory.getLogger(MerchantRepositoryImpl.class);
	
	private static final int SOCKET_TIMEOUT = 10000;
	private static final int CONNECT_TIMEOUT = 5000;
	private static final String RESOURCE = "/accountmanagement/monitor";
	
	private String hostname = "api.test.paysafe.com";
	
	public MerchantRepositoryImpl() {
		super();
		init();
	}

	@PostConstruct
	private void init() {
		Unirest.setTimeouts(CONNECT_TIMEOUT, SOCKET_TIMEOUT);
		Unirest.setObjectMapper(new ObjectMapperDelegator());
	}

	@Override
	public void updateHostName(String hostname) {
		this.hostname = hostname;
	}
	
	@Override
	public ServerStatusReport verify() {
		
		try {
			String url = "http://" + hostname + RESOURCE;
			HttpResponse<EndpointStatus> getRequest = get(url, EndpointStatus.class);
			return new ServerStatusReport(isAvailable(getRequest), new Date());
		} catch (UnirestException e) {
			LOG.error("error verifying endpoint status", e);
			return new ServerStatusReport(false, new Date());
		}
	}

	protected HttpResponse<EndpointStatus> get(String url, Class<EndpointStatus> responseType) throws UnirestException {
		return Unirest.get(url).asObject(responseType);
	}

	private boolean isAvailable(HttpResponse<EndpointStatus> getRequest) {
		return "READY".equals(getRequest.getBody().getStatus());
	}

}
