package sf.resources;

import java.io.IOException;
import java.util.HashMap;

import sf.http.HTTPManager;
import sf.http.HTTPRequestObject;
import sf.http.HTTPResponseObject;
import sf.http.HTTPManager.REQUEST_TYPE;


public class Utils {
	
	private static Utils _instance;
	
	private Utils(){}
	
	public static Utils getInstance()
	{
		if(_instance == null)
		{
			return new Utils();
		}
		else
		{
			return _instance;
		}
	}
	
	public void getAPIState()
	{
		String url = Constants.heartBeatURL;
		try 
		{
			HTTPManager man = new HTTPManager();
			HTTPResponseObject hrespObj = man.makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET,
					url, new HashMap<String, String>()));
			
			for(String key : hrespObj.getResponse().keySet())
			{
				System.out.println(key + ": " + hrespObj.getResponse().get(key));
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
