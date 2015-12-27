package sf.objects.http;

import sf.objects.queryresults.Order;
import sf.utilities.http.HTTPManager.REQUEST_TYPE;

public class HTTPRequestObject {
	private REQUEST_TYPE rt;
	private String url;
	private Order parameters;
	private Class<?> returnClass;
	
	public HTTPRequestObject(REQUEST_TYPE requestType, String url,
			Order parameters) {
		this.rt = requestType;
		this.url = url;
		this.parameters = parameters;
	}

	public HTTPRequestObject(REQUEST_TYPE requestType, String url,
			Order parameters, Class<?> returnClass) {
		this.rt = requestType;
		this.url = url;
		this.parameters = parameters;
		this.returnClass = returnClass;
	}

	public REQUEST_TYPE getRequestType() {
		return rt;
	}

	public String getUrl() {
		return url;
	}

	public Order getParameters() {
		return parameters;
	}

	public Class<?> getReturnClass() {
		return returnClass;
	}
}
