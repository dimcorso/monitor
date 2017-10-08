package com.paysafe.monitor.core.repository.impl;

import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {

			private ObjectMapper mapper = new ObjectMapper();

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return mapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public String writeValue(Object value) {
				try {
					return mapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void updateHostName(String hostname) {
		this.hostname = hostname;
	}
	
	@Override
	public ServerStatusReport verify() {
		
		HttpResponse<EndpointStatus> getRequest;
		try {
			getRequest = Unirest.get("http://" + hostname + "/" + RESOURCE).asObject(EndpointStatus.class);
			
			return new ServerStatusReport(isAvailable(getRequest), new Date());
		} catch (UnirestException e) {
			LOG.error("error verifying endpoint status", e);
			return new ServerStatusReport(false, new Date());
		}
	}

	private boolean isAvailable(HttpResponse<EndpointStatus> getRequest) {
		return "READY".equals(getRequest.getBody().getStatus());
	}

}
