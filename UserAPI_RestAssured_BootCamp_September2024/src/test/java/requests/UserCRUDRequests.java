package requests;

import java.io.IOException;

//import org.hamcrest.Matcher.*;
import org.testng.Assert;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import endPoints.UserModuleEndPoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import payloads.UserPayload;
import utilities.RestUtils;
import static io.restassured.RestAssured.*;

public class UserCRUDRequests extends RestUtils {
	
	static RequestSpecification getCommonRequestSpec(String hasAuth) {
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		
//		builder.setBaseUri(baseURL);
		builder.setContentType(ContentType.JSON);
		
		
		if(hasAuth.equalsIgnoreCase("auth")) 
			builder.setAuth(preemptive().basic(userName, passwd));
		
	       // Build the RequestSpecification
        RequestSpecification requestSpec = builder.build();
        
		return requestSpec;
		
	}
	static ResponseSpecification getCommonResponseSpec(){
        // Create a reusable ResponseSpecification
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder
//            .expectStatusCode(200)
            .expectHeader("Content-Type", "application/json");
//            .expectTime(lessThan(2000L));  // Validate that response time is less than 2 seconds
//            .expectBody("user_id", notNullValue());
        
        ResponseSpecification responseSpec = responseSpecBuilder.build();
        
		return responseSpec;
	}

	public static Response postRequest(UserPayload objUserPayload,String sheetName,int rowNo,String isvalidEndpoint) throws IOException {
		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.CREATEUSER.getPath();
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.CREATEUSERINVALIDENDPOINT.getPath();			
		}
		System.out.println("endpoint" + endpoint);	
		
		Response response = RestAssured
				.given()
				.spec(getCommonRequestSpec("auth"))
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
//				.header("Content-Type", "application/json")
//				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.log().all()
				.assertThat()
//				.statusCode(201)
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
//				.and()
//				.body(JsonSchemaValidator.matchesJsonSchema(userPostWithAllFieldsJson)) //Schema Validation
				.extract().response();


		System.out.println("Sheet name " + sheetName);
		System.out.println("rowNo " + rowNo);
		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 if(statusCode == 201)
		 {
//			if(userId1 == 0 ) {
			 	userId1 = response.getBody().jsonPath().getInt("user_id");
			 	userFirstName1 = response.getBody().jsonPath().getString("user_first_name");
		//		objUserPayload.setUser_id(userId1);
				System.out.println("userId1 " +userId1);
				System.out.println("userFirstName1 " +userFirstName1);
				
				response.then().body(JsonSchemaValidator.matchesJsonSchema(userPostWithAllFieldsJson)); //Schema Validation
				//response null check
				response.then().body("user_id", notNullValue(),"user_first_name",notNullValue(),"user_last_name",notNullValue(),"user_contact_number",notNullValue(),"user_email_id",notNullValue());

				//Data validation(Mandatory fields)
				response.then().body("user_first_name", equalToCompressingWhiteSpace(objUserPayload.getUser_first_name()))
				.body("user_last_name", equalToCompressingWhiteSpace(objUserPayload.getUser_last_name()))
				.body("user_contact_number", equalTo(Long.parseLong(objUserPayload.getUser_contact_number())))
				.body("user_email_id", equalToCompressingWhiteSpace(objUserPayload.getUser_email_id()))
				.body("userAddress.plotNumber", equalTo(objUserPayload.getUserAddress().getPlotNumber()))
				.body("userAddress.street", equalToCompressingWhiteSpace(objUserPayload.getUserAddress().getStreet()))
				.body("userAddress.state", equalToCompressingWhiteSpace(objUserPayload.getUserAddress().getState()))
				.body("userAddress.country", equalToCompressingWhiteSpace(objUserPayload.getUserAddress().getCountry()))
				.body("userAddress.zipCode", equalTo(Integer.parseInt(objUserPayload.getUserAddress().getZipCode())));
				
//			}else {
//				userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
//				userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
//		//		objUserPayload.setUser_id(userId1);
//				System.out.println("userId2 " +userIdMandatoryFields);
//				System.out.println("userFirstName2 " +userFirstNameMandatoryFields);
//				
//			}

		 //Data Validation
//	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
//	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
//	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
//	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 //status code validation
			if(isvalidEndpoint.equalsIgnoreCase("valid")) 	 
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(xlutils.getCellData(sheetName, rowNo, 10)));
			else
				Assert.assertEquals(response.getStatusCode(), 404);
		 
			//content type validation
//			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response postRequestNoAuth(UserPayload objUserPayload,String sheetName,int rowNo,String isvalidEndpoint) throws IOException {

		
		Response response = RestAssured
				.given()
				.spec(getCommonRequestSpec("noauth"))
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
//				.header("Content-Type", "application/json")
//				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(UserModuleEndPoints.endpoint.CREATEUSER.getPath())
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
				.extract().response();


		System.out.println("Sheet name " + sheetName);
		System.out.println("rowNo " + rowNo);
		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
		 //status code validation
		Assert.assertEquals(response.getStatusCode(), 401);
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}


	public static Response postRequestMandatoryField(UserPayload objUserPayload,String sheetName,int rowNo,String isvalidEndpoint) throws IOException {
		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.CREATEUSER.getPath();
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.CREATEUSERINVALIDENDPOINT.getPath();			
		}
		System.out.println("endpoint" + endpoint);	
		
		Response response = RestAssured
				.given()
				.spec(getCommonRequestSpec("auth"))
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
//				.header("Content-Type", "application/json")
//				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
//				.body("user_id", notNullValue(),"user_first_name",notNullValue(),"user_last_name",notNullValue(),"user_contact_number",notNullValue(),"user_email_id",notNullValue())
//				.body(JsonSchemaValidator.matchesJsonSchema(userPostWithAllFieldsJson)) //Schema Validation
				.extract().response();


		System.out.println("Sheet name " + sheetName);
		System.out.println("rowNo " + rowNo);
		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 if(statusCode == 201)
		 {
			 userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
			 userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
		//		objUserPayload.setUser_id(userId1);
				System.out.println("userIdMandatoryFields " +userIdMandatoryFields);
				System.out.println("userFirstName1 " +userFirstNameMandatoryFields);

				//response null check
				response.then().body("user_id", notNullValue(),"user_first_name",notNullValue(),"user_last_name",notNullValue(),"user_contact_number",notNullValue(),"user_email_id",notNullValue());

				//Data validation(Mandatory fields)
				response.then().body("user_first_name", equalToCompressingWhiteSpace(objUserPayload.getUser_first_name()))
				.body("user_last_name", equalToCompressingWhiteSpace(objUserPayload.getUser_last_name()))
				.body("user_contact_number", equalTo(Long.parseLong(objUserPayload.getUser_contact_number())))
				.body("user_email_id", equalToCompressingWhiteSpace(objUserPayload.getUser_email_id()));
				
//		 //Data Validation
//	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
//	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
//	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
//	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 //status code validation
			if(isvalidEndpoint.equalsIgnoreCase("valid")) 	 
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(xlutils.getCellData(sheetName, rowNo, 10)));
			else
				Assert.assertEquals(response.getStatusCode(), 404);
		 
			//content type validation
//			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response getAllUsers(String isvalidEndpoint,String expectedStatuscode,String hasAuthorization) throws IOException {

		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
		System.out.println("************expectedStatuscode****"+expectedStatuscode);
		System.out.println("************hasAuthorization****"+hasAuthorization);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.GETALLUSERS.getPath();
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.GETALLUSERSINVALIDENDPOINT.getPath();			
		}
		System.out.println("#############endpoint##################" + endpoint);	

		Response response ; 
		

		response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.log().all()
				.when()
				.get(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
				.extract().response();
		

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
//		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

//		response.then().assertThat().
		 
		 //status code validation
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
//		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response getUserById(String isvalidUserId,String isvalidEndpoint,String hasAuthorization) throws IOException {

		System.out.println("************isvalidEndpoint****"+isvalidUserId);
		System.out.println("************userId1****"+userId1);
		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
//		System.out.println("************expectedStatuscode****"+expectedStatuscode);
		System.out.println("************hasAuthorization****"+hasAuthorization);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYID.getPath() + userId1;
		}
		else if(isvalidEndpoint.equalsIgnoreCase("invalid") && isvalidUserId.equalsIgnoreCase("valid")){
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYIDINVALIDENDPOINT.getPath() + userId1;
		}else if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("invalid")) {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYID.getPath() + InvalidUserId;
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYIDINVALIDENDPOINT.getPath();			
		}
		System.out.println("################## endpoint #####################" + endpoint);	

		Response response ; 
		
			response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.extract().response();

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().prettyPrint());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		//response null check
		if(response.getStatusCode() == 200) {
			response.then().body("user_id", notNullValue(),"user_first_name",notNullValue(),"user_last_name",notNullValue(),"user_contact_number",notNullValue(),"user_email_id",notNullValue());

		//Data validation
			response.then().body("user_id", equalTo(userId1));
		} 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
//		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	
	public static Response getUserByFirstName(String isvalidUserFirstName,String isvalidEndpoint,String hasAuthorization) throws IOException {

		System.out.println("************isvalidEndpoint****"+isvalidUserFirstName);
		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
//		System.out.println("************expectedStatuscode****"+expectedStatuscode);
		System.out.println("************hasAuthorization****"+hasAuthorization);
		System.out.println("************userFirstName1****"+userFirstName1);
		System.out.println("************userFirstName1****"+InvalidUserFirstName);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserFirstName.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYFIRSTNAME.getPath() + userFirstName1;
		}
		else if(isvalidEndpoint.equalsIgnoreCase("invalid") && isvalidUserFirstName.equalsIgnoreCase("valid")){
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYFIRSTNAMEINVALIDENDPOINT.getPath() + userFirstName1;
		}else if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserFirstName.equalsIgnoreCase("invalid")) {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYFIRSTNAME.getPath() + InvalidUserFirstName;
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.GETUSERBYFIRSTNAMEINVALIDENDPOINT.getPath();			
		}
		System.out.println("################## endpoint #####################" + endpoint);	

		Response response ; 
		
			response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.log().all()
				.when()
				.get(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
				.extract().response();

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
//		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	
	public static Response deleteUserById(String isvalidUserId,String isvalidEndpoint,String hasAuthorization) throws IOException {

		System.out.println("************isvalidEndpoint****"+isvalidUserId);
		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
		System.out.println("************userId1****"+userId1);
//		System.out.println("************expectedStatuscode****"+expectedStatuscode);
		System.out.println("************hasAuthorization****"+hasAuthorization);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERID.getPath() + userId1;
		}
		else if(isvalidEndpoint.equalsIgnoreCase("invalid") && isvalidUserId.equalsIgnoreCase("valid")){
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERIDINVALID.getPath() + userId1;
		}else if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("invalid")) {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERID.getPath() + InvalidUserId;
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERIDINVALID.getPath();			
		}
		System.out.println("################## endpoint #####################" + endpoint);	

		Response response ; 
		
			response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.log().all()
				.when()
				.delete(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
				.extract().response();

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
//		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response deleteUserByFirstname(String isvalidUserName,String isvalidEndpoint,String hasAuthorization) throws IOException {

		System.out.println("************isvalidEndpoint****"+isvalidUserName);
		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
		System.out.println("************userId1****"+userId1);
		System.out.println("************hasAuthorization****"+hasAuthorization);
		System.out.println("************userFirstName1****"+userFirstName1);
		System.out.println("************userFirstName1****"+InvalidUserFirstName);
		System.out.println("userIdMandatoryFields " +userIdMandatoryFields);
		System.out.println("userFirstName1 " +userFirstNameMandatoryFields);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserName.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERFIRSTNAME.getPath() + userFirstNameMandatoryFields;
		}
		else if(isvalidEndpoint.equalsIgnoreCase("invalid") && isvalidUserName.equalsIgnoreCase("valid")){
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERFIRSTNAMEINVALID.getPath() + userFirstNameMandatoryFields;
		}else if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserName.equalsIgnoreCase("invalid")) {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERFIRSTNAME.getPath() + InvalidUserFirstName;
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.DELETEBYUSERFIRSTNAMEINVALID.getPath();			
		}
		System.out.println("################## endpoint #####################" + endpoint);	

		Response response ; 
		
			response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.log().all()
				.when()
				.delete(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
				.extract().response();

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
//		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response updateUser(UserPayload objUserPayload,String hasAuthorization, String isvalidEndpoint, String isvalidUserId) throws IOException {

		System.out.println("************isvalidUserId****"+isvalidUserId);
		System.out.println("************isvalidEndpoint****"+isvalidEndpoint);
		System.out.println("************userId1****"+userId1);
//		System.out.println("************expectedStatuscode****"+expectedStatuscode);
		System.out.println("************hasAuthorization****"+hasAuthorization);

		String endpoint ;
		if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("valid")) {
			endpoint = UserModuleEndPoints.endpoint.UPDATEUSER.getPath() + userId1;
		}
		else if(isvalidEndpoint.equalsIgnoreCase("invalid") && isvalidUserId.equalsIgnoreCase("valid")){
			endpoint = UserModuleEndPoints.endpoint.UPDATEUSERINVALIDENDPOINT.getPath() + userId1;
		}else if(isvalidEndpoint.equalsIgnoreCase("valid") && isvalidUserId.equalsIgnoreCase("invalid")) {
			endpoint = UserModuleEndPoints.endpoint.UPDATEUSER.getPath() + InvalidUserId;
		}
		else {
			endpoint = UserModuleEndPoints.endpoint.UPDATEUSERINVALIDENDPOINT.getPath();			
		}
		System.out.println("################## endpoint #####################" + endpoint);	
		
		Response response ; 
		
		 response = RestAssured
				.given()
				.spec(getCommonRequestSpec(hasAuthorization))
				.body(objUserPayload)
				.log().all()
				.when()
				.put(endpoint)
				.then()
	            .spec(getCommonResponseSpec())  // Apply the ResponseSpecification
				.assertThat()
	            .time(lessThan(2000L))  // Validate that response time is less than 2 seconds
				.log().all()
//				.body(JsonSchemaValidator.matchesJsonSchema(programPostjson)) //Schema Validation
				.extract().response();

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 if(statusCode == 200)
		 {
//			if(userId1 == 0 ) {
			 	userId1 = response.getBody().jsonPath().getInt("user_id");
			 	userFirstName1 = response.getBody().jsonPath().getString("user_first_name");
			 	
				response.then().body(JsonSchemaValidator.matchesJsonSchema(userPostWithAllFieldsJson)); //Schema Validation
			 	
				//response null check
				response.then().body("user_id", notNullValue(),"user_first_name",notNullValue(),"user_last_name",notNullValue(),"user_contact_number",notNullValue(),"user_email_id",notNullValue());

				//Data validation(Mandatory fields)
				response.then().body("user_id", equalTo(userId1))
				.body("user_first_name", equalToCompressingWhiteSpace(objUserPayload.getUser_first_name()))
				.body("user_last_name", equalToCompressingWhiteSpace(objUserPayload.getUser_last_name()))
				.body("user_contact_number", equalTo(Long.parseLong(objUserPayload.getUser_contact_number())))
				.body("user_email_id", equalToCompressingWhiteSpace(objUserPayload.getUser_email_id()));

				//		objUserPayload.setUser_id(userId1);
				System.out.println("userId1 " +userId1);
				System.out.println("userFirstName1 " +userFirstName1);


		 //Data Validation
//	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
//	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
//	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
//	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 
			//content type validation
			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

}
