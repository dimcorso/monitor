package com.paysafe.monitor.deamon.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.paysafe.monitor.core.model.Config;

public class MyRouteBuilder extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		from("scheduler://monitor?delay=1000")
			.to("bean:monitorFacade?method=fire")
		.end();
		
		restConfiguration()
			.component("restlet")
			.port("8090")
			.contextPath("api")
			.bindingMode(RestBindingMode.json);
		
		rest("/monitor")
			.post("config").type(Config.class)
				.to("bean:monitorFacade?method=configure");

	}

}
