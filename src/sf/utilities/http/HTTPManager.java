package sf.utilities.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import sf.constants.Constants;
import sf.objects.http.HTTPRequestObject;
import sf.objects.http.HTTPRequestObject.Order;
import sf.objects.http.HTTPResponseObject;

public class HTTPManager 
{
	private String USER_AGENT = "Mozilla/5.0";
	private String ACCEPT_LANGUAGE = "en-US;en;q=0.5";
	private String CONTENT_TYPE = "application/json";

	public HTTPManager(){}

	/**
	 * To make custom requests
	 * **/
	public enum REQUEST_TYPE {POST, GET};
	protected HTTPResponseObject makeRequest(HTTPRequestObject hreqObj)
	{
		HTTPResponseObject hrespObj = null;
		try 
		{
			System.out.println(hreqObj.getRequestType() + ": " + hreqObj.getUrl());

			if(hreqObj.getRequestType() == REQUEST_TYPE.GET)
			{
				hrespObj = sendGET(hreqObj);
			}
			else
			{
				hrespObj = sendPOST(hreqObj);
			}
			System.out.println("Response Code: " + hrespObj.getResponseCode());
			System.out.println("Response Class: " + hrespObj.getResponse().getClass());
		}
		catch (IOException e) 
		{
			System.out.println("Something went wrong with making the " + hreqObj.getRequestType()  + " request."
					+ "\nMessage: " + e.getMessage()
					+ "\nCause: " + e.getCause());
		}

		return hrespObj;
	}

	private  HTTPResponseObject sendGET(HTTPRequestObject hreqObj) throws IOException
	{
		URL link = new URL(hreqObj.getUrl());

		HttpURLConnection huc = (HttpURLConnection) link.openConnection();
		huc.setRequestMethod("GET");
		huc.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = huc.getResponseCode();

		Object responseObject = null;
		if(hreqObj.getClass() == null)
		{
			//pass a null return class to figure out what the keys in the response are
			HashMap<String, Object> responseMap = 
					new Gson().fromJson(new JsonReader(new InputStreamReader(huc.getInputStream())),
							new TypeToken<HashMap<String, Object>>(){}.getType());

			System.out.println("Keys: " + responseMap.keySet());
		}
		else
		{
			if(responseCode == 200)
			{
				responseObject = new Gson().fromJson(new JsonReader(new InputStreamReader(huc.getInputStream())),
						hreqObj.getReturnClass());				
			}
			else
			{
				responseObject = new Gson().fromJson(new JsonReader(new InputStreamReader(huc.getErrorStream())),
						hreqObj.getReturnClass());	
			}
		}

		huc.disconnect();
		return new HTTPResponseObject(responseCode, responseObject);
	}

	private HTTPResponseObject sendPOST(HTTPRequestObject hreqObj) throws IOException
	{
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

		//understanding the response
		int responseCode = huc.getResponseCode();

		BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));

		StringBuilder sb = new StringBuilder();
		while(br.ready())
		{
			sb.append(br.readLine());
		}

		br.close();
		huc.disconnect();

		String json = sb.toString();

		return new HTTPResponseObject(responseCode, json);
	}
}


