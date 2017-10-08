package com.paysafe.monitor.core.repository.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ObjectMapperDelegator implements com.mashape.unirest.http.ObjectMapper {
	private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

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
}