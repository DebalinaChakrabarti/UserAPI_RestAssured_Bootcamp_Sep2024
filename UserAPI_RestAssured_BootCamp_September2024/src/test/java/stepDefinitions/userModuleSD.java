package stepDefinitions;

import static io.restassured.RestAssured.baseURI;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import requestBody.UserPayloadBody;
import requests.UserCRUDRequests;
import utilities.RestUtils;

public class userModuleSD extends RestUtils {


	@Given("Admin creates GET Request for User Module")
	public void admin_creates_get_request_for_user_module() {
		baseURI = rb_routes.getString("BaseUrl");
		
	log.info("*** Admin sets BaseURL For UserModule ***" + baseURI);
	}

	@When("Admin sends HTTP request with {string} and {string}")
	public void admin_sends_http_request_with_and(String string, String string2) {
	}

	@Then("Admin recieves response status code {string} with {string} message with {string} and {string}")
	public void admin_recieves_response_status_code_with_message_with_and(String statuscode, String statustext, String endpoint, String authorization) throws IOException {

		userResponse = UserCRUDRequests.getAllUsers(endpoint, statuscode, authorization);
	}

	@Given("Admin sets POST request to create User with request body for {string} and {int}")
	public void admin_sets_post_request_to_create_user_with_request_body_for_and(String sheet, int rowno) {
//		if(baseURI.isBlank())
			baseURI = rb_routes.getString("BaseUrl");
		
		log.info("*** Admin sets BaseURL For UserModule ***" + baseURI);
	}

	@When("Admin sends HTTP request with {string} and request body for {string} and {int}")
	public void admin_sends_http_request_with_and_request_body_for_and(String endpoint, String sheet, int rowno) throws Exception, IOException {
		userResponse = UserCRUDRequests.postRequest(UserPayloadBody.PostUserBody(sheet, rowno), sheet, rowno,endpoint);
		System.out.println("userResponse" + userResponse.getBody().asString());
	}

	@Then("Admin recieves response code")
	public void admin_recieves_response_code() {
	}
	
	@Given("Admin sets POST request to create User without Authorization")
	public void admin_sets_post_request_to_create_user_without_authorization() {
		baseURI = rb_routes.getString("BaseUrl");
		
	log.info("*** Admin sets BaseURL For UserModule ***" + baseURI);
	}

	@When("Admin sends HTTP request without Authorization with {string} request body for {string} and {int}")
	public void admin_sends_http_request_without_authorization_with_request_body_for_and(String endpoint, String sheet, int rowno) throws Exception, IOException {
		userResponse = UserCRUDRequests.postRequestNoAuth(UserPayloadBody.PostUserBody(sheet, rowno), sheet, rowno,endpoint);

	}

	@When("Admin sends HTTPS Request with authorization {string} endpoint {string} to retrieve User Details by UserID {string}")
	public void admin_sends_https_request_with_authorization_endpoint_to_retrieve_user_details_by_user_id(String authorization, String endpoint, String userid) throws IOException {
		userResponse = UserCRUDRequests.getUserById(userid, endpoint, authorization);
	}

	@Then("Admin receives Status {int} and {string} with response body for User Module")
	public void admin_receives_status_and_with_response_body_for_user_module(int statuscode, String statustext) {
		 //status code validation
		Assert.assertEquals(userResponse.getStatusCode(),statuscode );
	}

	@Given("Admin sets PUT request to update user details with request body for {string} and {int}")
	public void admin_sets_put_request_to_update_user_details_with_request_body_for_and(String string, Integer int1) {
		baseURI = rb_routes.getString("BaseUrl");
		
	log.info("*** Admin sets BaseURL For PUT ***" + baseURI);
	}

	@When("Admin sends HTTP PUT request with {string} and {string} to update user by {string} with request body for {string} and {int}")
	public void admin_sends_http_put_request_with_and_to_update_user_by_with_request_body_for_and(String authorization, String endpoint, String userid, String sheetname, Integer rowno) throws IOException {
		userResponse = UserCRUDRequests.updateUser(UserPayloadBody.PostUserBody(sheetname, rowno), authorization, endpoint, userid);
	}

	@Then("Admin recieves response code {int} and {string}")
	public void admin_recieves_response_code_and(Integer statuscode, String respMsg) {
		 //status code validation
		Assert.assertEquals(userResponse.getStatusCode(),statuscode );
		 //status Line validation
		Assert.assertTrue(userResponse.getStatusLine().contains(respMsg));
	}
	
	
	
	@When("Admin sends HTTPS Request with authorization {string} endpoint {string} to retrieve User Details by User First Name {string}")
	public void admin_sends_https_request_with_authorization_endpoint_to_retrieve_user_details_by_user_first_name(String authorization, String endpoint, String userfirstname) throws IOException {
		userResponse = UserCRUDRequests.getUserByFirstName(userfirstname, endpoint, authorization);
	}

	@Then("Admin receives Status {int} and {string} with response body for User Module.")
	public void admin_receives_status_and_with_response_body_for_user_module(Integer statuscode, String statustext) {
		 //status code validation
		Assert.assertEquals(userResponse.getStatusCode(),statuscode );
	}

	
	@Given("Admin creates DELETE Request for User Module")
	public void admin_creates_delete_request_for_user_module() {
		baseURI = rb_routes.getString("BaseUrl");
		
	log.info("*** Admin sets BaseURL For DELETE ***" + baseURI);
	}

	@When("Admin sends HTTPS Request with authorization {string} endpoint {string} to delete User by UserID {string}")
	public void admin_sends_https_request_with_authorization_endpoint_to_delete_user_by_user_id(String authorization, String endpoint, String userid) throws IOException {
		userResponse = UserCRUDRequests.deleteUserById(userid, endpoint, authorization);
	}

	@Then("Admin receives Status {int} and {string} and {string} with response body for User Module")
	public void admin_receives_status_and_and_with_response_body_for_user_module(Integer statuscode, String statustext, String respMsg) {
		 //status code validation
		Assert.assertEquals(userResponse.getStatusCode(),statuscode );
		 //status Line validation
		Assert.assertTrue(userResponse.getStatusLine().contains(respMsg));
//		Assert.assertEquals(userResponse.getStatusLine(),respMsg.trim() );
	}

	
	@When("Admin sends HTTPS Request with authorization {string} endpoint {string} to delete User by User First Name {string}")
	public void admin_sends_https_request_with_authorization_endpoint_to_delete_user_by_user_first_name(String authorization, String endpoint, String userfirstname) throws IOException {
		userResponse = UserCRUDRequests.deleteUserByFirstname(userfirstname, endpoint, authorization);
	}

	@Then("Admin receives Status {int} and {string} and {string} with response body for User Module.")
	public void admin_receives_status_and_and_with_response_body_for_user(Integer statuscode, String statustext, String respMsg) {
		 //status code validation
		Assert.assertEquals(userResponse.getStatusCode(),statuscode );
		 //status Line validation
		Assert.assertTrue(userResponse.getStatusLine().contains(respMsg));
	}


	@When("Admin sends HTTP request with {string} and request body for {string} and {int} to create user with Mandatory fields only")
	public void admin_sends_http_request_with_and_request_body_for_and_to_create_user_with_mandatory_fields_only(String endpoint, String sheet, Integer rowno) throws IOException {
		userResponse = UserCRUDRequests.postRequestMandatoryField(UserPayloadBody.PostUserBodyWithMandatoryField(sheet, rowno), sheet, rowno,endpoint);
		System.out.println("userResponse" + userResponse.getBody().asString());

	}

}
