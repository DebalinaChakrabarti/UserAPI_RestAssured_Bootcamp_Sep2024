@UserModule
Feature: Testing CRUD operation in User

Background: User sets the Basic authorization with Username and Password and enters the base URL  


  @Get_Request(get_all_Users)
  Scenario Outline: Check Admin able to retrieve all Users details with valid or invalid datas
    Given Admin creates GET Request for User Module
    When Admin sends HTTP request with "<endpoint>" and "<authorization>"
    Then Admin recieves response status code "<statuscode>" with "<statustext>" message with "<endpoint>" and "<authorization>"

    Examples: 
      | endpoint | statuscode | statustext		| authorization	|  
      | valid    |        401 | Unauthorized	|	noAuth				|
      | valid    |        200 | OK			  		|	auth					|
			| invalid  |        404 | Not Found 		|	auth					|


@POST_Request_to_Create_User
   Scenario Outline: Check if Admin is able to create new User with valid or invalid inputs
    Given Admin sets POST request to create User with request body for "<sheet>" and <row>
    When Admin sends HTTP request with "<endpoint>" and request body for "<sheet>" and <row>
    Then Admin recieves response code

    Examples: 
      | sheet				| row |	endpoint	|
      | UserModule  |   1 |	invalid		|
      | UserModule  |   1 |	valid			|
      | UserModule  |   3 |	valid			|
      | UserModule  |   4 |	valid			|
      | UserModule  |   5 |	valid			|
      | UserModule  |   6 |	valid			|
      | UserModule  |   7 |	valid			|
      | UserModule  |   8 |	valid			|
      | UserModule  |   9 |	valid			|
      | UserModule  |   10 |	valid			|
      | UserModule  |   11 |	valid			|
      | UserModule  |   12 |	valid			|
      | UserModule  |   13 |	valid			|
      | UserModule  |   14 |	valid			|
      | UserModule  |   15 |	valid			|
      | UserModule  |   16 |	valid			|
      | UserModule  |   17 |	valid			|
      | UserModule  |   18 |	valid			|
      | UserModule  |   19 |	valid			|
      | UserModule  |   20 |	valid			|
      | UserModule  |   21 |	valid			|
      | UserModule  |   22 |	valid			|
      | UserModule  |   23 |	valid			|
      | UserModule  |   24 |	valid			|
      | UserModule  |   25 |	valid			|
      | UserModule  |   26 |	valid			|		
      | UserModule  |   27 |	valid			|
      | UserModule  |   28 |	valid			|
      | UserModule  |   29 |	valid			|
      | UserModule  |   30 |	valid			|
      | UserModule  |   31 |	valid			|
      | UserModule  |   32 |	valid			|
      | UserModule  |   33 |	valid			|
      | UserModule  |   34 |	valid			|
      | UserModule  |   35 |	valid			|
      | UserModule  |   36 |	valid			|
      | UserModule  |   37 |	valid			|
      | UserModule  |   38 |	valid			|
      | UserModule  |   39 |	valid			|
      | UserModule  |   40 |	valid			|
      
      
@POST_Request_to_Create_User_without_authorization
   Scenario Outline: Check if Admin is able to create new User without Authorization
    Given Admin sets POST request to create User without Authorization
    When Admin sends HTTP request without Authorization with "<endpoint>" request body for "<sheet>" and <row>
    Then Admin recieves response code

    Examples: 
      | sheet				| row |	endpoint	|
      | UserModule  |   1 |	valid			|


 	@GetRequest(Get_User_By_UserID)
Scenario Outline: Check if admin is able to retrieve User Details by UserID with valid and invalid inputs
Given Admin creates GET Request for User Module
When Admin sends HTTPS Request with authorization "<authorization>" endpoint "<endpoint>" to retrieve User Details by UserID "<userid>" 
Then Admin receives Status <statuscode> and "<statustext>" with response body for User Module   

	Examples:
			 |	userid 	| endpoint	| statuscode	|	statustext		|  authorization	|
			 |	valid	 	|	valid			|	401					|	Unauthorized	|       noauth    |                                           
			 |	valid	 	|	valid			|	200					|	OK						|       auth      |                                         
			 |	valid	 	|	invalid		|	404					|	Not Found			|       auth      |                                         
			 |	invalid	|	valid			|	404					|	Not Found			|       auth			|                                              


   @PUT_Request_to_Update_User
  Scenario Outline: Check if Admin is able to update user with valid or invalid datas
    Given Admin sets PUT request to update user details with request body for "<sheet>" and <row>
    When Admin sends HTTP PUT request with "<authorization>" and "<endpoint>" to update user by "<userid>" with request body for "<sheet>" and <row>
    Then Admin recieves response code <statuscode> and "<respMsg>"

    Examples: 
      | sheet      | row |	endpoint	|	userid	|	authorization	|		statuscode		|		respMsg		 |
      | UserModule |  48 |	valid			|	valid		|	noauth			 	|			401					| HTTP/1.1 401 |
      | UserModule |  48 |	invalid		|	valid		|	auth					|			404					| HTTP/1.1 404 |
      | UserModule |  48 |	valid			|	invalid	|	auth				 	|			404					| HTTP/1.1 404 |
      | UserModule |  48 |	valid			|	valid		|	auth			 		|			200					| HTTP/1.1 200 |
 
 
      
   	@GetRequest(Get_User_By_UserFirstName)
Scenario Outline: Check if admin is able to retrieve User Details by UserFirstName with valid and invalid inputs
Given Admin creates GET Request for User Module
When Admin sends HTTPS Request with authorization "<authorization>" endpoint "<endpoint>" to retrieve User Details by User First Name "<userfirstname>" 
Then Admin receives Status <statuscode> and "<statustext>" with response body for User Module.   

	Examples:
			 |	userfirstname 	| endpoint	| statuscode	|	statustext		|  authorization	|
			 |	valid	 					|	valid			|	401					|	Unauthorized	|       noauth    |                                           
			 |	valid	 					|	valid			|	200					|	OK						|       auth      |                                         
			 |	valid	 					|	invalid		|	404					|	Not Found			|       auth      |                                         
			 |	invalid					|	valid			|	404					|	Not Found			|       auth			|                                              
      

@DeleteRequest(Delete_User_By_UserID)
Scenario Outline: Check if admin is able to Delete User by UserID with valid and invalid inputs
Given Admin creates DELETE Request for User Module
When Admin sends HTTPS Request with authorization "<authorization>" endpoint "<endpoint>" to delete User by UserID "<userid>" 
Then Admin receives Status <statuscode> and "<statustext>" and "<respMsg>" with response body for User Module   

	Examples:
			 |	userid 	| endpoint	| statuscode	|	statustext		|  authorization	|		respMsg		 |
			 |	valid	 	|	valid			|	401					|	Unauthorized	|       noauth    | HTTP/1.1 401 |                                           
			 |	valid	 	|	valid			|	200					|	OK						|       auth      | HTTP/1.1 200 |                                         
			 |	valid	 	|	invalid		|	404					|	Not Found			|       auth      | HTTP/1.1 404 |                                         
			 |	invalid	|	valid			|	404					|	Not Found			|       auth			| HTTP/1.1 404 |                                              


                                                               
@POST_Request_to_Create_User_With_Mandatory_Fields
   Scenario Outline: Check if Admin is able to create new User with Mandatory fields only
    Given Admin sets POST request to create User with request body for "<sheet>" and <row>
    When Admin sends HTTP request with "<endpoint>" and request body for "<sheet>" and <row> to create user with Mandatory fields only
    Then Admin recieves response code

    Examples: 
      | sheet				| row |	endpoint	|
      | UserModule  |  44 |	valid			|

 
 @DeleteRequest(Delete_User_By_UserFirstName)
Scenario Outline: Check if admin is able to Delete User by UserFirstName with valid and invalid inputs
Given Admin creates DELETE Request for User Module
When Admin sends HTTPS Request with authorization "<authorization>" endpoint "<endpoint>" to delete User by User First Name "<userfirstname>" 
Then Admin receives Status <statuscode> and "<statustext>" and "<respMsg>" with response body for User Module.   

	Examples:
			 |	userfirstname 	| endpoint	| statuscode	|	statustext		|  authorization	|		respMsg		 |
			 |	valid	 					|	valid			|	401					|	Unauthorized	|       noauth    | HTTP/1.1 401 |                                        
			 |	valid	 					|	valid			|	200					|	OK						|       auth      | HTTP/1.1 200 |                                        
			 |	valid	 					|	invalid		|	404					|	Not Found			|       auth      | HTTP/1.1 404 |                                      
			 |	invalid					|	valid			|	404					|	Not Found			|       auth			| HTTP/1.1 404 |                                            
 