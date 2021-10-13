package com.hmrc.camel.api.errors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GenerateFailureResponse  implements Processor{

	    @Autowired
	    ObjectMapper objectMapper;
	    

	public void process(Exchange exchange) throws Exception {
        exchange.getOut().setFault(true);
        exchange.getMessage().setBody("Invalid Input Request Missing Required Fields");
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
	}

}
