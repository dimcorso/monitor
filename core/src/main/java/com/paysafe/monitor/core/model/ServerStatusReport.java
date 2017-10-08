package com.paysafe.monitor.core.model;

import java.util.Date;

public class ServerStatusReport {

	private boolean available;
	private Date date;

	public ServerStatusReport(boolean available, Date date) {

		setAvailable(available);
		setDate(date);
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
