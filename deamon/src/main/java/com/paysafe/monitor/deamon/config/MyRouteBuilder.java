package com.paysafe.monitor.deamon.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paysafe.monitor.core.model.Config;

public class MyRouteBuilder extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		
		/*
		 * a more advanced error handling should be plugged here. 
		 * Log the stacktrace and send a Json response to the client
		 * Also send the proper http status in the response.
		 * 
		 *  For now we put this to avoid the stack trace to be sent to the client
		 */
		onException(JsonProcessingException.class)
			.handled(true)
			.transform().simple("${exception.message}")
			.end();
	
		onException(Exception.class)
			.handled(true)
			.transform().simple("Internal Server Error")
			.end();
		
		
		
		from("scheduler://monitor?delay=1000")
			.to("bean:monitorFacade?method=tick")
		.end();
		
		restConfiguration()
			.component("restlet")
			.dataFormatProperty("prettyPrint", "true")
			.contextPath("api").port("8090")
			.bindingMode(RestBindingMode.json)
			.apiContextPath("/api-doc")
	            .apiProperty("api.title", "Paysafe Monitor API").apiProperty("api.version", "1.0.0")
	            .apiProperty("cors", "true")
	            ;
		
		rest("/monitor")
			.put("config").description("Configuration update endpoint").type(Config.class).outType(Config.class)
				.to("bean:monitorFacade?method=configure")
				
			.get("summary").description("Sever status summary endpoint")
				.to("bean:monitorFacade?method=buildReport");

	}

}
