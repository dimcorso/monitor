package com.paysafe.monitor.deamon.config;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		 from("stream:in?promptMessage=> ")
		 .process("gameProcessor")
		 .to("stream:out");
		 	
	}

}
