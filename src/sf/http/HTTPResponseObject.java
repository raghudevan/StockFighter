package sf.http;

import java.util.HashMap;

public class HTTPResponseObject 
{
	private int responseCode;
	private HashMap<String, Object> response;
	
	public HTTPResponseObject(int responseCode,
			HashMap<String, Object> response) 
	{
		this.responseCode = responseCode;
		this.response = response;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	public HashMap<String, Object> getResponse() {
		return response;
	}
}
