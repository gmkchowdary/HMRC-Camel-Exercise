package com.hmrc.camel.api.processor;

import java.math.BigDecimal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmrc.camel.api.request.HmrcEmployeeRequest;
import com.hmrc.camel.api.response.HmrcEmployeeResponse;

@Service
public class HmrcEmployeeProcessor  implements Processor{
	 @Autowired
	    private ObjectMapper objectMapper;

	@Override
	public void process(Exchange exchange) throws Exception {
		HmrcEmployeeRequest empRequest = objectMapper.readValue(exchange.getIn().getBody(String.class), HmrcEmployeeRequest.class);
		exchange.getIn().setBody(objectMapper.writeValueAsString(calculateBonus(empRequest)));
	}
	
	 public HmrcEmployeeResponse calculateBonus(HmrcEmployeeRequest employee) {
	        return new HmrcEmployeeResponse(
	                employee.getHmrcempId(),
	                "HMRC Employee FullName , " + employee.getHmrcempFullName(),
	                employee.getHmrcempSalary(),
	                employee.getHmrcempSalary().multiply(BigDecimal.valueOf(0.12))
	        );
	    }
}
