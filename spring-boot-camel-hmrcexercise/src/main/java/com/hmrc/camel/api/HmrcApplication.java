package com.hmrc.camel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;



@SpringBootApplication(exclude = {WebSocketServletAutoConfiguration.class,
        AopAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class,
        EmbeddedWebServerFactoryCustomizerAutoConfiguration.class})
public class HmrcApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HmrcApplication.class, args);
	}
	
	

}
