package sf.http;

import java.util.HashMap;

import sf.http.HTTPManager.REQUEST_TYPE;

public class HTTPRequestObject 
{
	private REQUEST_TYPE rt;
	private String url;
	private HashMap<String, String> parameters;
	
	public HTTPRequestObject(REQUEST_TYPE requestType, String url,
			HashMap<String, String> parameters) 
	{
		this.rt = requestType;
		this.url = url;
		this.parameters = parameters;
	}

	public REQUEST_TYPE getRequestType() {
		return rt;
	}

	public String getUrl() {
		return url;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}
}
