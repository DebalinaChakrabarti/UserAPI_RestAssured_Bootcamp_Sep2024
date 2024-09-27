package requests;

import java.io.IOException;

import org.testng.Assert;


import endPoints.UserModuleEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import payloads.UserPayload;
import utilities.RestUtils;

public class UserCRUDRequests extends RestUtils {

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
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(endpoint)
				.then()
				.log().all()
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
//			}else {
//				userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
//				userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
//		//		objUserPayload.setUser_id(userId1);
//				System.out.println("userId2 " +userIdMandatoryFields);
//				System.out.println("userFirstName2 " +userFirstNameMandatoryFields);
//				
//			}

		 //Data Validation
	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 //status code validation
			if(isvalidEndpoint.equalsIgnoreCase("valid")) 	 
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(xlutils.getCellData(sheetName, rowNo, 10)));
			else
				Assert.assertEquals(response.getStatusCode(), 404);
		 
			//content type validation
			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

	public static Response postRequestNoAuth(UserPayload objUserPayload,String sheetName,int rowNo,String isvalidEndpoint) throws IOException {

		
		Response response = RestAssured
				.given()
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
				.header("Content-Type", "application/json")
//				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(UserModuleEndPoints.endpoint.CREATEUSER.getPath())
				.then()
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
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.post(endpoint)
				.then()
				.log().all()
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
			 userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
			 userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
		//		objUserPayload.setUser_id(userId1);
				System.out.println("userIdMandatoryFields " +userIdMandatoryFields);
				System.out.println("userFirstName1 " +userFirstNameMandatoryFields);
//			}else {
//				userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
//				userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
//		//		objUserPayload.setUser_id(userId1);
//				System.out.println("userId2 " +userIdMandatoryFields);
//				System.out.println("userFirstName2 " +userFirstNameMandatoryFields);
//				
//			}

		 //Data Validation
	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 //status code validation
			if(isvalidEndpoint.equalsIgnoreCase("valid")) 	 
				Assert.assertEquals(response.getStatusCode(), Integer.parseInt(xlutils.getCellData(sheetName, rowNo, 10)));
			else
				Assert.assertEquals(response.getStatusCode(), 404);
		 
			//content type validation
			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {

			response = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all()
				.extract().response();
		}
		else {
			response = RestAssured
			.given()
			.header("Content-Type", "application/json")
			.log().all()
			.when()
			.get(endpoint)
			.then()
			.log().all()
			.extract().response();
		}

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
//		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
		 //status code validation
		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {

			response = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all()
				.extract().response();
		}
		else {
			response = RestAssured
			.given()
			.header("Content-Type", "application/json")
			.log().all()
			.when()
			.get(endpoint)
			.then()
			.log().all()
			.extract().response();
		}

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {
			
			response = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all()
				.extract().response();
		}
		else {
			response = RestAssured
			.given()
			.auth().none()
			.header("Content-Type", "application/json")
			.log().all()
			.when()
			.get(endpoint)
			.then()
			.log().all()
			.extract().response();
		}

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {

			response = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.log().all()
				.when()
				.delete(endpoint)
				.then()
				.log().all()
				.extract().response();
		}
		else {
			response = RestAssured
			.given()
			.header("Content-Type", "application/json")
			.log().all()
			.when()
			.delete(endpoint)
			.then()
			.log().all()
			.extract().response();
		}

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {

			response = RestAssured
				.given()
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.log().all()
				.when()
				.delete(endpoint)
				.then()
				.log().all()
				.extract().response();
		}
		else {
			response = RestAssured
			.given()
			.header("Content-Type", "application/json")
			.auth().none()
			.log().all()
			.when()
			.delete(endpoint)
			.then()
			.log().all()
			.extract().response();
		}

		System.out.println("Status code " + response.getStatusCode());
		System.out.println("Response Time " + response.getTime());
		System.out.println("Content Type " + response.getContentType());
		System.out.println("Body " +response.getBody().asString());
		System.out.println("Status Line " +response.getStatusLine());
		statusCode= response.getStatusCode();

		 
//		 //status code validation
//		Assert.assertEquals(response.getStatusCode(),Integer.parseInt(expectedStatuscode) );
		
		//content type validation
		Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
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
		
		if(hasAuthorization.equalsIgnoreCase("auth")) {
		 response = RestAssured
				.given()
//				.baseUri(baseURL)
//				.contentType(ContentType.JSON)
				.header("Content-Type", "application/json")
				.auth().basic(userName, passwd)
				.body(objUserPayload)
				.log().all()
				.when()
				.put(endpoint)
				.then()
				.log().all()
//				.body(JsonSchemaValidator.matchesJsonSchema(programPostjson)) //Schema Validation
				.extract().response();
		}else {
			 response = RestAssured
						.given()
//						.baseUri(baseURL)
//						.contentType(ContentType.JSON)
						.header("Content-Type", "application/json")
						.auth().none()
						.body(objUserPayload)
						.log().all()
						.when()
						.put(endpoint)
						.then()
						.log().all()
//						.body(JsonSchemaValidator.matchesJsonSchema(programPostjson)) //Schema Validation
						.extract().response();
			
		}

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
		//		objUserPayload.setUser_id(userId1);
				System.out.println("userId1 " +userId1);
				System.out.println("userFirstName1 " +userFirstName1);
//			}else {
//				userIdMandatoryFields = response.getBody().jsonPath().getInt("user_id");
//				userFirstNameMandatoryFields = response.getBody().jsonPath().getString("user_first_name");
//		//		objUserPayload.setUser_id(userId1);
//				System.out.println("userId2 " +userIdMandatoryFields);
//				System.out.println("userFirstName2 " +userFirstNameMandatoryFields);
//				
//			}

		 //Data Validation
	    Assert.assertEquals(objUserPayload.getUser_first_name(), response.jsonPath().getString("user_first_name"));
	    Assert.assertEquals(objUserPayload.getUser_last_name(), response.jsonPath().getString("user_last_name"));
	    Assert.assertEquals(objUserPayload.getUser_contact_number(), response.jsonPath().getString("user_contact_number"));
	    Assert.assertEquals(objUserPayload.getUser_email_id(), response.jsonPath().getString("user_email_id"));

		 }
		 
		 
			//content type validation
			Assert.assertTrue(response.getContentType().equalsIgnoreCase("application/json"));
		
		return response;
	}

}
