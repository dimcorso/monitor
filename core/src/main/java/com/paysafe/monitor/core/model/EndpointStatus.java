package com.paysafe.monitor.core.model;

public class EndpointStatus {

	private String status;

	public EndpointStatus(String status) {
		super();
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
