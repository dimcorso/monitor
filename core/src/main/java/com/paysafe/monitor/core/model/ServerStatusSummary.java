package com.paysafe.monitor.core.model;

import java.util.Date;

public class ServerStatusSummary {

	private boolean available;
	private Date from;
	private Date to;

	public ServerStatusSummary(boolean available, Date from, Date to) {
		this.available = available;
		this.from = from;
		this.to = to;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date date) {
		this.to = date;
		
	}
}
