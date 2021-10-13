package com.hmrc.camel.api.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hmrc.camel.api.request.HmrcEmployeeRequest;

public class WireMockJunitTest {
	
	
	private static final String URL = "/camel/api/bean";

    private WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8888));

    @Test
    public void givenProgrammaticallyManagedServer_whenUsingSimpleStubbing_thenCorrect() throws URISyntaxException, JSONException {
        wireMockServer.start();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        configureFor("localhost", 8888);

        String expectedJson = "{\r\n" +
                "    \"hmrcempId\": 1000,\r\n" +
                "    \"hmrcempFullName\": \"Hello, Sankar\",\r\n" +
                "    \"hmrcempSalary\": 10000,\r\n" +
                "    \"bonus\": 1200.0\r\n" +
                "}";

        stubFor(post(urlEqualTo(URL)).willReturn(aResponse().withBody(expectedJson)));

        URI url = new URI("http://localhost:8888/camel/api/bean");
        HmrcEmployeeRequest objEmp = new HmrcEmployeeRequest(1000, "Sankar", BigDecimal.valueOf(10000));
        HttpEntity<HmrcEmployeeRequest> requestEntity = new HttpEntity<>(objEmp, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        JSONAssert.assertEquals(expectedJson, responseEntity.getBody(), false);
        wireMockServer.stop();
    }

}
