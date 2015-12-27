package sf.objects.queryresults;

public class APIResultObject {

	private String error = "";
	private Boolean ok = Boolean.FALSE;
	
	public String getError() {
		return error;
	}

	public Boolean getOk() {
		return ok;
	}

	public APIResultObject(Boolean ok) {
		this.ok = ok;
	}
	
	public APIResultObject(Boolean ok, String error) {
		this.ok = ok;
		this.error = error;
	}
}
