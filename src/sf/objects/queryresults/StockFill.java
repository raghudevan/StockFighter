package sf.objects.queryresults;

public class StockFill extends Stock {

	private String ts;

	public StockFill(long price, Integer quantity, String ts) {
		super(price, quantity, Boolean.FALSE);
		this.ts = ts;
	}
	
	public String getTimestamp() {
		return this.ts;
	}
}
