package sf.objects.http;

public class HTTPResponseObject 
{
	private int responseCode;
	private Object response;
	
	public HTTPResponseObject(int responseCode,
			Object response) {
		this.responseCode = responseCode;
		this.response = response;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public Object getResponse() {
		return response;
	}
}
