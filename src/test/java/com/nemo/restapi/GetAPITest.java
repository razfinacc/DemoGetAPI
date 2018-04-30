package com.nemo.restapi;
import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nemo.restapi.BaseTest;
import com.nemo.restapi.RestClient;

/**
 * @author rajekumar
 */
public class GetAPITest extends BaseTest {

	private String finalUrl;
	private RestClient restClient;
	private CloseableHttpResponse httpresponse;

	private JSONArray jsonArray;

	@BeforeMethod
	public void setUP() throws ClientProtocolException, IOException, JSONException {
		String serviceUrl = prop.getProperty("BaseUrl");
		String apiserviceUrl = prop.getProperty("ServiceURL");

		// https://reqres.in//api/users
		finalUrl = serviceUrl + apiserviceUrl;
		System.out.println("API URL: " + finalUrl);

		// }

		// @Test(priority = 1)
		// public void getAPITest() throws ClientProtocolException, IOException,
		// JSONException {
		restClient = new RestClient();
		httpresponse = restClient.get(finalUrl);

		// Get Status Code
		int statuscode = httpresponse.getStatusLine().getStatusCode();
		System.out.println("Status Code: " + statuscode);

		Assert.assertEquals(statuscode, RESPONE_STATUS_CODE_200, "Status code not OK");

		// Get JSON String
		String strjson = EntityUtils.toString(httpresponse.getEntity(), "UTF-8");
		JSONObject jobject = new JSONObject(strjson);
		System.out.println("Response JSON: " + jobject);

		// Get ALL Headers
		Header[] headersArray = httpresponse.getAllHeaders(); // Convert array into Hash-Map
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array: " + allHeaders);

		//
		JSONObject restResoponseObject = jobject.getJSONObject("RestResponse");
		jsonArray = restResoponseObject.getJSONArray("result");
	}

	@Test
	public void testCapital() throws JSONException {
		// System.out.println("Result Length ="+jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject countryObject = (JSONObject) jsonArray.get(i);
			if (countryObject.getString("abbr").equals("MA")) {
				Assert.assertEquals(countryObject.getString("capital"), "Boston");
				Assert.assertEquals(countryObject.getString("largest_city"), "Boston");
			}
		}
	}

	/* Enter state abbreviation */

	@Test(priority =1)
	public void getinputCapital() throws JSONException {
		// System.out.println("Result Length ="+jsonArray.length());
		String state1 = "Enter state abbreviation";
		String state2 = "Enter state name";
		System.out.println(state1+ " OR "+state2);
		String userabbr = getUserInput(state1); 
		System.out.println(userabbr);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject countryObject = (JSONObject) jsonArray.get(i);
			String abbrevation = countryObject.getString("abbr");
			String stateName = countryObject.getString("name");
			System.out.println(abbrevation);
			System.out.println(stateName);
			if (userabbr != null && abbrevation.equals(userabbr) || stateName.equals(userabbr)) {
				System.out.println("Entered");
				System.out.println(userabbr);
				System.out.println(abbrevation);
				System.out.println(stateName);
				String ucapital = countryObject.getString("capital");
				String uname = countryObject.getString("name");
				String largestCity = countryObject.getString("largest_city");
				System.out.println("The Capital city is of " + userabbr + " is " + ucapital);
				System.out.println("The City name is  " + userabbr + " is " + uname);
				System.out.println("The Largest city is of " + userabbr + " is " + largestCity);
				Assert.assertEquals(abbrevation, userabbr);
				Assert.assertEquals(stateName, userabbr);
				} else {
					System.out.println("Not Entered");
					String ucapital = countryObject.getString("abbr");
					String largestCity = countryObject.getString("largest_city");
					Assert.assertNotEquals(abbrevation, ucapital, "Abbreviation NOT OK");
					Assert.assertNotEquals(stateName, userabbr, "CITY Name entered NOT OK");
				}
		}
	}

	@AfterMethod
	public void teardown() {
		System.out.println("DONE");
	}
	
	
	public static String getUserInput(String str) {
		Scanner scan = new Scanner(System.in);
		System.out.println(str);
		String userInput = scan.next();
		return userInput;
	}
}


