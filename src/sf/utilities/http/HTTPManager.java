package sf.utilities.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import sf.constants.Constants;
import sf.objects.http.HTTPRequestObject;
import sf.objects.http.HTTPResponseObject;
import sf.objects.queryresults.Order;

public class HTTPManager 
{
	private String USER_AGENT = "Mozilla/5.0";
	private String ACCEPT_LANGUAGE = "en-US;en;q=0.5";
	private String CONTENT_TYPE = "application/json";

	public HTTPManager(){}

	/**
	 * To make custom requests
	 * The RequestObject will contain:
	 * 1. The REQUEST_TYPE 
	 * 2. An url(Against which the request is made)
	 * 3. An order(parameters)
	 * 4. The returnClass(the value object to reuturn as a response to the request)
	 * **/
	public enum REQUEST_TYPE {POST, GET};
	protected HTTPResponseObject makeRequest(HTTPRequestObject hreqObj) {
		HTTPResponseObject hrespObj = null;
		try {
			System.out.println(hreqObj.getRequestType() + ": " + hreqObj.getUrl());

			if(hreqObj.getRequestType() == REQUEST_TYPE.GET) {
				hrespObj = sendGET(hreqObj);
			}
			else {
				hrespObj = sendPOST(hreqObj);
			}
			System.out.println("Response Code: " + hrespObj.getResponseCode());
			System.out.println("Response Class: " + hrespObj.getResponse().getClass());
		} catch (IOException e) {
			//			System.out.println("Something went wrong with making the " + hreqObj.getRequestType()  + " request."
			//					+ "\nMessage: " + e.getMessage()
			//					+ "\nCause: " + e.getCause());
			hrespObj = new HTTPResponseObject(-1, e.getMessage());
		}

		return hrespObj;
	}

	private  HTTPResponseObject sendGET(HTTPRequestObject hreqObj)
			throws IOException {
		URL link = new URL(hreqObj.getUrl());

		HttpURLConnection huc = (HttpURLConnection) link.openConnection();
		huc.setRequestMethod("GET");
		huc.setRequestProperty("User-Agent", USER_AGENT);
		huc.setRequestProperty("X-Starfighter-Authorization", Constants.apiKey);

		//construct a response
		HTTPResponseObject hresObj = constructResponse(huc, hreqObj);

		huc.disconnect();
		return hresObj;
	}

	private HTTPResponseObject sendPOST(HTTPRequestObject hreqObj)
			throws IOException {
		URL link = new URL(hreqObj.getUrl());
		Order parameters = hreqObj.getParameters();
		String paramJSON = new Gson().toJson(parameters, Order.class);

		HttpURLConnection huc = (HttpURLConnection) link.openConnection();
		huc.setRequestMethod("POST");
		huc.setRequestProperty("User-Agent", USER_AGENT);
		huc.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
		huc.setRequestProperty("Content-Type", CONTENT_TYPE);
		huc.setRequestProperty("Content-Length", Integer.toString(paramJSON.length()));
		huc.setRequestProperty("Content-Language", ACCEPT_LANGUAGE);
		huc.setRequestProperty("X-Starfighter-Authorization", Constants.apiKey);

		//to ensure we can write to the streams
		huc.setUseCaches(Boolean.FALSE);
		huc.setDoInput(Boolean.TRUE);
		huc.setDoOutput(Boolean.TRUE);

		//sending the request - TODO: need to understand this fully - why isn't this needed in the GET request?
		DataOutputStream dos = new DataOutputStream(huc.getOutputStream());
		dos.writeBytes(paramJSON);
		dos.flush();
		dos.close();

		//construct the response the response
		HTTPResponseObject hresObj = constructResponse(huc, hreqObj);

		huc.disconnect();

		return hresObj;
	}

	private HTTPResponseObject constructResponse(HttpURLConnection huc,
			HTTPRequestObject hreqObj)
					throws IOException {
		InputStream is = null;
		Object responseObject = null;
		int responseCode = huc.getResponseCode();
		
		if(responseCode == 200) {
			is = huc.getInputStream();
		} else {
			is = huc.getErrorStream();
		}

		//pass a null return class to figure out what the keys in the response are
		if(hreqObj.getReturnClass() == null) {
			HashMap<String, Object> responseMap = 
					new Gson().fromJson(new JsonReader(new InputStreamReader(is)),
							new TypeToken<HashMap<String, Object>>(){}.getType());
			
			/*
			 * return the key set so that that information may be used to
			 * make a value object for this response type
			 * */
			responseObject = responseMap.keySet();
		} else {
			responseObject = new Gson().fromJson(new JsonReader(new InputStreamReader(is)),
					hreqObj.getReturnClass());	
		}

		return new HTTPResponseObject(responseCode, responseObject);
	}
}


