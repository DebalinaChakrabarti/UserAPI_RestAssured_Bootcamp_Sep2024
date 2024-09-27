package utilities;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.AddressPayload;
import payloads.UserPayload;

public class RestUtils {

	public static ResourceBundle rb_routes = ResourceBundle.getBundle("routes");
	public static ResourceBundle rb_credentials = ResourceBundle.getBundle("credentials");
	public static XLReader xlutils=new XLReader(rb_routes.getString("XLpath"));

	//Schema Path
	public static File userPostWithAllFieldsJson = xlutils.getJSONFile(rb_routes.getString("programPostSchemajson"));

	public static Logger log = LogManager.getLogger();

	public static String baseURL = rb_routes.getString("BaseUrl");
	public static String userName = rb_credentials.getString("userName");
	public static String passwd = rb_credentials.getString("passwd");

		//PayLoad Objects
		public static UserPayload objUserPayload=new UserPayload();
		public static AddressPayload objAddressPayload=new AddressPayload();
		public static RequestSpecification request;
		public static Response userResponse;


	
	public static String extractResponse;
	public static int statusCode;
	public static int responseBody;
	public static int userId1;
	public static String userFirstName1;
	public static int userIdMandatoryFields;
	public static String userFirstNameMandatoryFields;
	public static int InvalidUserId = 5000;
	public static String InvalidUserFirstName = "zz";



}
