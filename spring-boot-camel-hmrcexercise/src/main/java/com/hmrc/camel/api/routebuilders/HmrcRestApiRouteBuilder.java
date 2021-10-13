package com.hmrc.camel.api.routebuilders;



import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jsonvalidator.JsonValidationException;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hmrc.camel.api.errors.GenerateFailureResponse;
import com.hmrc.camel.api.processor.HmrcEmployeeProcessor;



@Component
public class HmrcRestApiRouteBuilder  extends RouteBuilder {
	

    @Value("${server.port}")
    String serverPort;
    
    @Value("${context.path}")
    String contextPath;
    
    @Autowired
    GenerateFailureResponse generateFailureResponse;
    
    @Autowired
    HmrcEmployeeProcessor  hmrcEmployeeProcessor;

    
    @Override
    public void configure() {
        onException(JsonValidationException.class).handled(true).process(generateFailureResponse);
        restConfiguration().contextPath(contextPath)
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Sample REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/api/").description("Camel Integrations REST API Service")
                .id("api-route")
                .post("/bean")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.off)
                .enableCORS(true).route()
                .to("json-validator:hmrcemployee-request-schema.json")
                .to("direct:hmrcemployeeService");

        from("direct:hmrcemployeeService")
                .process(hmrcEmployeeProcessor)
                .to("json-validator:hmrcemployee-response-schema.json")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
    

}
