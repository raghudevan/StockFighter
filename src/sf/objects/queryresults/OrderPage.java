package sf.objects.queryresults;

import java.util.ArrayList;

public class OrderPage extends APIResultObject {

	private String venue;
	private ArrayList<Stock> asks;
	private ArrayList<Stock> bids;
	private String symbol;
	private String ts;

	public String getVenue() {
		return venue;
	}

	/*
	 * Listings looking to sell
	 * */
	public ArrayList<Stock> getAsks(Integer howManyToRetrieve) {
		return asks;
	}
	
	public ArrayList<Stock> getAsks() {
		return getAsks(asks.size());
	}

	/*
	 * Listings looking to buy
	 * */
	public ArrayList<Stock> getBids(Integer howManyToRetrieve) {
		return bids;
	}
	
	public ArrayList<Stock> getBids() {
		return getBids(bids.size());
	}

	public String getSymbol() {
		return symbol;
	}

	public String getTimestamp() {
		return ts;
	}
	
	/*
	 * TODO: Assuming that the asks and bids are sorted
	 * will probably have to implement some sort of sort here.
	 * A simple sort will not suffice though since i'll have 
	 * to keep in mind the quantity of stock available
	 * */
	
	/*
	 * Asks - people asking to sell
	 * This returns the best buying price
	 * */
	public Stock getBestBuy() {
		return asks == null ? new Stock(-1, 0, Boolean.FALSE) : asks.get(0);
	}
	
	/*
	 * Bids - people asking to buy
	 * This returns best selling price for the stock
	 * */
	public Stock getBestSell() {
		return bids == null ? new Stock(-1, 0, Boolean.TRUE) : bids.get(0);
	}

	public OrderPage(Boolean ok, String venue,
			ArrayList<Stock> asks, ArrayList<Stock> bids,
			String symbol, String timestamp) {
		super(ok);
		this.venue = venue;
		this.asks = asks;
		this.bids = bids;
		this.symbol = symbol;
		this.ts = timestamp;
	}

	public OrderPage(Boolean ok, String venue,
			ArrayList<Stock> asks, ArrayList<Stock> bids,
			String symbol, String timestamp, String error) {
		super(ok, error);
		this.venue = venue;
		this.asks = asks;
		this.bids = bids;
		this.symbol = symbol;
		this.ts = timestamp;
	}

}
