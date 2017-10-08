package com.paysafe.monitor.core.repository.impl;

import org.springframework.stereotype.Repository;

import com.paysafe.monitor.core.repository.MerchantRepository;

@Repository
public class MerchantRepositoryImpl implements MerchantRepository {

	private String hostname = "localhost";

	@Override
	public void updateHostName(String hostname) {
		this.hostname = hostname;

	}

}
