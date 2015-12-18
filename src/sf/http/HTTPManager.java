package sf.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HTTPManager 
{
	private String USER_AGENT = "Mozilla/5.0";
	private String ACCEPT_LANGUAGE = "en-US;en;q=0.5";
	
	public enum REQUEST_TYPE {POST, GET};
	
	public HTTPManager(){}
	
	public HTTPResponseObject makeRequest(HTTPRequestObject hro)
			throws IOException
	{
		switch(hro.getRequestType())
		{
		case POST:
			return sendPost(hro.getUrl(), hro.getParameters());
		case GET:
			return sendGET(hro.getUrl());
		}
		
		return null;
	}
	
	private HTTPResponseObject sendGET(String link) 
			throws IOException
	{
		URL url = new URL(link);
		
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		huc.setRequestProperty("User-Agent", USER_AGENT);
		
		System.out.println("Sending GET request to: " + link);
		int responseCode = huc.getResponseCode();
		System.out.println("Response code: " + responseCode);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		
		StringBuilder sb = new StringBuilder();
		while(br.ready())
		{
			sb.append(br.readLine() + "\n");
		}
		
		HashMap<String, Object> json = new Gson().fromJson(sb.toString(),
				new TypeToken<HashMap<String, Object>>(){}.getType());
		
		br.close();
		return new HTTPResponseObject(responseCode, json);
	}
	
	private HTTPResponseObject sendPost(String link, HashMap<String, String> parameters)
			throws IOException
	{
		URL url = new URL(link);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		
		
		huc.setRequestMethod("POST");
		huc.setRequestProperty("User-Agent", USER_AGENT);
		huc.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
		
		return null;
	}
}


