## HMRC Spring Boot Camel Exercise

Project  contains API for integrating Spring Boot with Apache Camel. 

### JSON Schemas

The schemas used for validating request JSON and response JSON are stored under resources folder.

### Steps to run the Project

Execute below command form the project folder 


`mvn spring-boot:run`
	
Please execute below url to make a POST http request to:

`http://localhost:8080/camel/api/bean` 

sample payload 

`{"hmrcempId": 1, "hmrcempFullName": "Sankar", "hmrcempSalary" : 10000}`

Submit the request

### Functionality

##### Positive Test Case 
 
Once request is submitted, The Request message will be validated against the schema InputSchema.json,If the JSON is not complaint with JSON Schema then a response with detailed list of errrors is generated and sent back to the client.

If the content of JSON message is valid then the bonus for that employee is calculated based on the salary and JSON response is generated.
 
The generated response is validated against another schema OutputSchema.json, if there are any validation issues, an error list is populated in a JSON and returned with HTTP Status Code 500.

If the validation passes, the response message along with calculated bonus attribute is passed back to client with HTTP Status code 200.


##### Negative Test case 

For a negative test, user can remove any one of the attributes in the request JSON. The validation will kick in and sends response with validation errors. 

### Docker

There is a docker file in the root folder which can be used to build image and run 
1. To Build a docker image run below command<br />
	docker build --tag springbootcamel:1.0 .

2. To run the container, run below command and then postman can be used to send requests<br />
	docker run --publish 8080:8080 --detach --name sbci springbootcamel:1.0

