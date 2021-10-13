package com.hmrc.camel.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmrc.camel.api.HmrcApplication;
import com.hmrc.camel.api.request.HmrcEmployeeRequest;
import com.hmrc.camel.api.response.HmrcEmployeeResponse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmrcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HmrcRestApiRouteBuilderTest {
	
	  private static HttpHeaders headers;

	    @Autowired
	    ObjectMapper objectMapper;


	    @BeforeClass
	    public static void runBeforeAllTestMethods() {
	        headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	    }

	    @Test
	    public void shouldCalculateEmployeeBonus() throws URISyntaxException {
	        URI url = new URI("http://localhost:8082/camel/api/bean");
	        HmrcEmployeeRequest hmrRequest = new HmrcEmployeeRequest(1000, "Sankar", BigDecimal.valueOf(10000));
	        HttpEntity<HmrcEmployeeRequest> requestEntity = new HttpEntity<>(hmrRequest, headers);
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<HmrcEmployeeResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, HmrcEmployeeResponse.class);
	        HmrcEmployeeResponse employeeResponse = responseEntity.getBody();
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertNotNull(employeeResponse);
	        assertEquals("1200.00", employeeResponse.getBonus().toString());
	    }


	    @Test(expected = HttpServerErrorException.InternalServerError.class)
	    public void shouldReturnValidationExceptionForInvalidEmployeeRequest() throws URISyntaxException {
	        URI url = new URI("http://localhost:8082/camel/api/bean");
	        HmrcEmployeeRequest hmrcEmployeeRequest = new HmrcEmployeeRequest();
	        hmrcEmployeeRequest.setHmrcempId(123);
	        hmrcEmployeeRequest.setHmrcempSalary(BigDecimal.valueOf(10000));
	        HttpEntity<HmrcEmployeeRequest> requestEntity = new HttpEntity<>(hmrcEmployeeRequest, headers);
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	    }


}
