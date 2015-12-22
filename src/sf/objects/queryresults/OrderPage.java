package sf.objects.queryresults;

import java.util.ArrayList;

public class OrderPage extends APIResultObject {

	private String venue;
	private ArrayList<Stock> asks;
	private ArrayList<Stock> bids;
	private String symbol;
	private String timestamp;

	public String getVenue() {
		return venue;
	}

	public ArrayList<Stock> getAsks() {
		return asks;
	}

	public ArrayList<Stock> getBids() {
		return bids;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public OrderPage(Boolean ok, String venue,
			ArrayList<Stock> asks, ArrayList<Stock> bids,
			String symbol, String timestamp) 
	{
		super(ok);
		this.venue = venue;
		this.asks = asks;
		this.bids = bids;
		this.symbol = symbol;
		this.timestamp = timestamp;
	}
	
	public OrderPage(Boolean ok, String venue,
			ArrayList<Stock> asks, ArrayList<Stock> bids,
			String symbol, String timestamp, String error) 
	{
		super(ok, error);
		this.venue = venue;
		this.asks = asks;
		this.bids = bids;
		this.symbol = symbol;
		this.timestamp = timestamp;
	}
	
}
