package com.hmrc.camel.api.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.hmrc.camel.api.processor.HmrcEmployeeProcessor;
import com.hmrc.camel.api.request.HmrcEmployeeRequest;
import com.hmrc.camel.api.response.HmrcEmployeeResponse;


@RunWith(SpringRunner.class)
public class HmrcEmployeeProcessorTest {
	
	 @Test
	    public void whenSalaryProvided_thenReturnBonus() {
	        HmrcEmployeeProcessor employeeProcessor = new HmrcEmployeeProcessor();
	        HmrcEmployeeRequest employeeRequest = new HmrcEmployeeRequest(1, "TestEmployee", new BigDecimal(1000));
	        HmrcEmployeeResponse employeeResponse = employeeProcessor.calculateBonus(employeeRequest);
	        Assert.assertEquals(employeeResponse.getBonus().longValue(), new BigDecimal(120).longValue());
	    }

}
