package com.paysafe.monitor.deamon.config;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.paysafe.monitor.core.config.CoreConfig;

@Configuration
@Import(CoreConfig.class)
@ComponentScan("com.paysafe.monitor.daemon.config")
public class MyRouteConfiguration extends CamelConfiguration {
	
	@Autowired
	private MyRouteBuilder myRouteBuilder;

	
	@Bean
	public MyRouteBuilder routerBuilder () {
		return new MyRouteBuilder();
	}
	
	@Bean
	public Processor gameProcessor() {
		return new Processor() {
			
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
			}
		};
	}
	
	@Override
	public List<RouteBuilder> routes() {
		return Arrays.asList(myRouteBuilder);
	}
}
