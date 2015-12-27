package sf.objects.queryresults;

public class VenueStatusObject extends APIResultObject {

	private String venue;

	public String getVenue() {
		return venue;
	}

	public VenueStatusObject(Boolean ok, String venue) {
		super(ok);
		this.venue = venue;
	}

	public VenueStatusObject(Boolean ok, String error, String venue) {
		super(ok, error);
		this.venue = venue;
	}
}
