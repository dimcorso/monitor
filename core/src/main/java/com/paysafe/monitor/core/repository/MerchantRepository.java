package com.paysafe.monitor.core.repository;

import com.paysafe.monitor.core.model.ServerStatusReport;

public interface MerchantRepository {

	void updateHostName(String hostname);

	ServerStatusReport verify();

}
