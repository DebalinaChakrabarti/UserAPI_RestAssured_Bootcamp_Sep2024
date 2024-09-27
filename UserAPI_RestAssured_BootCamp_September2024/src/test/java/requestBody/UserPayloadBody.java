package requestBody;

import java.io.IOException;
import java.util.Random;

import payloads.UserPayload;
import utilities.RestUtils;

public class UserPayloadBody extends RestUtils{

	public static UserPayload PostUserBody(String sheetName,int rowNo) throws IOException  
	{
		
		int expectedStatusCode = Integer.parseInt(xlutils.getCellData(sheetName, rowNo, 10));
		objUserPayload.setUser_first_name(xlutils.getCellData(sheetName, rowNo, 1));
		objUserPayload.setUser_last_name(xlutils.getCellData(sheetName, rowNo, 2));
//		if (expectedStatusCode == 409) {
		objUserPayload.setUser_contact_number(xlutils.getCellData(sheetName, rowNo, 3));
		objUserPayload.setUser_email_id(xlutils.getCellData(sheetName, rowNo, 4));
//		}else
//		{
//		objUserPayload.setUser_contact_number(Long.toString(getUniquePhoneNumber()));
//		objUserPayload.setUser_email_id(getUniqueEmail());
//		}
		objAddressPayload.setPlotNumber(xlutils.getCellData(sheetName, rowNo, 5));
		objAddressPayload.setStreet(xlutils.getCellData(sheetName, rowNo, 6));
		objAddressPayload.setState(xlutils.getCellData(sheetName, rowNo, 7));
		objAddressPayload.setCountry(xlutils.getCellData(sheetName, rowNo, 8));
		objAddressPayload.setZipCode(xlutils.getCellData(sheetName, rowNo, 9));
		objUserPayload.setUserAddress(objAddressPayload);
		
		System.out.println("*******UserPayload**********" +objUserPayload);
		
		return objUserPayload;
	
		
	}
	
	public static long getUniquePhoneNumber() {
		Random random = new Random();
		long min = 1000000000L; // Smallest 10-digit number
		long max = 9999999999L; // Largest 10-digit number

		long randomLong = min + ((long) (random.nextDouble() * (max - min + 1)));

		return randomLong;
	}

	public static String getUniqueEmail() {

		java.util.Random random = new java.util.Random();
		int random_int = random.nextInt(400);
		String randomEmail = "Rita" + random_int + "@gmail.com";
		return randomEmail;
	}
	public static UserPayload PostUserBodyWithMandatoryField(String sheetName,int rowNo) throws IOException  
	{
		
		objUserPayload.setUser_first_name(xlutils.getCellData(sheetName, rowNo, 1));
		objUserPayload.setUser_last_name(xlutils.getCellData(sheetName, rowNo, 2));
		objUserPayload.setUser_contact_number(xlutils.getCellData(sheetName, rowNo, 3));
		objUserPayload.setUser_email_id(xlutils.getCellData(sheetName, rowNo, 4));
		objUserPayload.setUserAddress(objAddressPayload);
		
		System.out.println("*******PostUserBodyWithMandatoryField**********" +objUserPayload);
		
		return objUserPayload;
	
		
	}

}
