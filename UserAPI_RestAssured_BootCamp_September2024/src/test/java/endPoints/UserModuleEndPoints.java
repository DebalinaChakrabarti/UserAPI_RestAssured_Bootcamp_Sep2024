package endPoints;

public class UserModuleEndPoints {

	public enum endpoint	{
		CREATEUSER("/createusers"),
		CREATEUSERINVALIDENDPOINT("/createusersss"),
		GETALLUSERS("/users"),
		GETALLUSERSINVALIDENDPOINT("/usersssss"),
		GETUSERBYID("/user/"),
		GETUSERBYIDINVALIDENDPOINT("/use/"),
		GETUSERBYFIRSTNAME("/users/username/"),
		GETUSERBYFIRSTNAMEINVALIDENDPOINT("/users/userna/"),
		UPDATEUSER("/updateuser/"),
		UPDATEUSERINVALIDENDPOINT("/updateus/"),
		DELETEBYUSERID("/deleteuser/"),
		DELETEBYUSERIDINVALID("/deleteus/"),
		DELETEBYUSERFIRSTNAME("/deleteuser/username/"),
		DELETEBYUSERFIRSTNAMEINVALID("/deleteuser/userna/");
		
		private final String path;
		
		endpoint(String path) {
			this.path=path;
		}
		
		public String getPath() {
	        return path;
	    }
	}
}
