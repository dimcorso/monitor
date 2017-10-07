package com.paysafe.monitor.deamon.config;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		from("scheduler://monitor?delay=5000")
			.to("bean:monitorFacade?method=fire")
		.end();

	}

}
