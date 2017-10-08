package com.paysafe.monitor.core.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitor.core.model.ServerStatusReport;
import com.paysafe.monitor.core.model.ServerStatusSummary;
import com.paysafe.monitor.core.repository.ExecutionHistoryRepository;
import com.paysafe.monitor.core.repository.MerchantRepository;

@Service
public class EndpointStatusServiceImpl implements com.paysafe.monitor.core.service.EndpointStatusService {

	private static final Logger LOG = LoggerFactory.getLogger(EndpointStatusServiceImpl.class);
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private ExecutionHistoryRepository executionHistoryRepository;
	
	@Override
	public void verify() {
		LOG.info("Verifying endpoint status");
		ServerStatusReport report = merchantRepository.verify();
		executionHistoryRepository.updateLastExecution(report);
	}

	@Override
	public void configure(String hostname) {
		merchantRepository.updateHostName(hostname);
	}
	
	@Override
	public List<ServerStatusSummary> buildReport() {
		List<ServerStatusReport> history = executionHistoryRepository.getHistory();
		LinkedList<ServerStatusSummary> intervals = new LinkedList<>();
		history
		.stream()
			.sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
			.reduce(
				intervals, 
				(cumulative, item) -> {
					updateLastInterval(cumulative, item);
					if (cumulative.isEmpty() || !sameAsLastOne(cumulative, item)) {
						cumulative.add(new ServerStatusSummary(item.isAvailable(),item.getDate(), new Date()));
					} 
					return cumulative;
				}, 
				(t1, t2) -> t1);
				
		return intervals;
         
	}

	private boolean sameAsLastOne(LinkedList<ServerStatusSummary> cumulative, ServerStatusReport item) {
		return cumulative.getLast().isAvailable() == item.isAvailable();
	}

	private void updateLastInterval(LinkedList<ServerStatusSummary> list, ServerStatusReport item) {
		
		if (!list.isEmpty()) {
			list.getLast().setTo(item.getDate());
		}
	}

}
