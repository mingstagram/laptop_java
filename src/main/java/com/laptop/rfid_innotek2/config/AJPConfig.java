package com.laptop.rfid_innotek2.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.var;

@Configuration
public class AJPConfig {
	
	@Value("${tomcat.ajp.port}")
	private int port;
	
	@Value("${tomcat.ajp.protocal}")
	String ajpProtocal;
	
	@Bean
	public ServletWebServerFactory servletContainer() {
		 
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		
		tomcat.addAdditionalTomcatConnectors(createAjpConnector());
		
		return tomcat;
		
	}
	
	private Connector createAjpConnector() {
		Connector ajpConnector = new Connector(ajpProtocal);
		ajpConnector.setPort(port);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");
		((AbstractAjpProtocol<?>)ajpConnector.getProtocolHandler()).setSecretRequired(false);
		return ajpConnector;
	}

}
