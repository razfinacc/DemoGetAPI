package com.nemo.restapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

/**
 * This is used to perform Http connection with Server.
 * 
 * @author rajekumar
 */
public class RestClient {

	// Create GET method
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {
		// create one client connection and return one closable HTTP client object
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // http get request
		CloseableHttpResponse httpresponse = httpclient.execute(httpget); // Hit the GET URL
		return httpresponse;
	}

	// Create POST method with JSON Pay-load as new StringEntity along with
	// HashMap
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException, JSONException {
		// create one client connection and return one closable HTTP client object
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); // Post request
		httppost.setEntity(new StringEntity(entityString)); // For Pay-load

		// for Headers
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse httpresponse = httpclient.execute(httppost); // Hit the GET URL
		return httpresponse;
	}
}
