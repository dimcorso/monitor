package com.paysafe.monitor.deamon.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.paysafe.monitor.core.facade.MonitorFacade;

public class MyRouteBuilder extends RouteBuilder {

	@Autowired
	private MonitorFacade facade;
	
	@Override
	public void configure() throws Exception {
		from("scheduler://monitor?delay=5000")
			.choice()
				.when((e-> facade.canFire()))
					.to("bean:monitorFacade?method=fire")
		.end();

	}

}
