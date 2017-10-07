package com.paysafe.monitor.core.repository.impl;

import com.paysafe.monitor.core.repository.MerchantRepository;

public class MerchantRepositoryImpl implements MerchantRepository {

	private String hostname = "localhost";

	@Override
	public void updateHostName(String hostname) {
		this.hostname = hostname;

	}

}
