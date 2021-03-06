package sf.utilities;

import java.util.ArrayList;
import java.util.HashMap;

import sf.constants.Constants;
import sf.objects.http.HTTPRequestObject;
import sf.objects.http.HTTPResponseObject;
import sf.objects.queryresults.APIResultObject;
import sf.objects.queryresults.LastQuote;
import sf.objects.queryresults.Order;
import sf.objects.queryresults.Order.DIRECTION;
import sf.objects.queryresults.OrderBookIndex;
import sf.objects.queryresults.OrderPage;
import sf.objects.queryresults.OrderResponse;
import sf.objects.queryresults.OrderStatus;
import sf.utilities.http.HTTPManager;

public class StockFighterUtils extends HTTPManager {
	private static StockFighterUtils _instance;
	public static StockFighterUtils getInstance() {
		if(_instance == null)
		{
			_instance = new StockFighterUtils();
		}
		return _instance;
	}

	private StockFighterUtils() {}

	/*
	 * URLs used by the methods defined below
	 * */
	private static String checkAPIHeartbeat_URL = 
			"https://api.stockfighter.io/ob/api/heartbeat";
	private static String checkVenueHeartbeat_URL =
			"https://api.stockfighter.io/ob/api/venues/TESTEX/heartbeat";

	private static String listStocks_URL = 
			"https://api.stockfighter.io/ob/api/venues/OGEX/stocks";

	/**
	 * Checks and returns the status of the stock fighter API
	 * **/
	public Boolean isAPIAlive() {
		System.out.println("Getting API status...");
		return performCheck(checkAPIHeartbeat_URL); 
	}

	/**
	 * Defaults to checking the status of TESTEX
	 * **/
	public Boolean isVenueAlive() {
		return isVenueAlive("TESTEX");
	}

	/**
	 * Checks and returns the status of the venue specified by venueName
	 * **/
	public Boolean isVenueAlive(String venueName) {
		System.out.println("Checking for " + venueName + " status...");
		return performCheck(checkVenueHeartbeat_URL.replace("TESTEX",
				venueName));
	}

	private Boolean performCheck(String checkURL) {
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, checkURL, 
						new Order(), APIResultObject.class));

		APIResultObject aro = (APIResultObject) hrespObj.getResponse();
		return aro.getOk();
	}

	public ArrayList<String> listStocks(String venueName) {
		System.out.println("Listing stocks at " + venueName + ": ");

		String url = listStocks_URL.replace("OGEX", venueName);
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, 
						new Order(), OrderBookIndex.class));

		OrderBookIndex obi = (OrderBookIndex) hrespObj.getResponse();
		return obi.getSymbols();
	}

	/**
	 * Returns the current status of the a 
	 * stock at a particular venue
	 * TODO: Should i store this? Do i need just a snapshot
	 * at any given time?
	 * **/
	private HashMap<String, OrderPage> orderBook = new HashMap<String, OrderPage>();
	private HashMap<String, OrderPage> getOrderBook(ArrayList<Order> orders) {
		HashMap<String, OrderPage> requestedPartOfTheBook = new HashMap<String, OrderPage>();
		for(Order order : orders) {
			if(!orderBook.containsKey(order.getSymbol()) || 
					order.getShouldRehash()) {
				//make the call to refresh the state of the orderBook
				String url = Constants.baseURL +
						"/venues/" + order.getVenue() +
						"/stocks/" + order.getSymbol();

				HTTPResponseObject hrespObj =
						makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url,
								order, OrderPage.class));

				OrderPage op = (OrderPage) hrespObj.getResponse();
				orderBook.put(order.getSymbol(), op);
			} else {
				//there is no need to change the state of the orderBook
			}
			requestedPartOfTheBook.put(order.getSymbol(), orderBook.get(order.getSymbol()));
		}

		return requestedPartOfTheBook;
	}

	public Order getBestQuote(Order order) {
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.add(order);

		OrderPage op = getOrderBook(orders).get(order.getSymbol());

		if(order.getDirection() == DIRECTION.buy) {
			order.setPrice(op.getBestBuy().getPrice());
			order.setQuantity(op.getBestBuy().getQuantity());
		} else {
			order.setPrice(op.getBestSell().getPrice());
			order.setQuantity(op.getBestSell().getQuantity());
		}

		return order;
	}

	/**
	 * Places an order as specified by the input parameter order
	 * OrderType: limit, market, fill-or-kill, immediate-or-cancel
	 * **/
	public OrderResponse placeOrder(Order order) {
		System.out.println("Placing " + order.getDirection() + " order");
		//construct the URL
		String url = Constants.baseURL +
				"/venues/" + order.getVenue() +
				"/stocks/" + order.getSymbol() +
				"/orders";

		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.POST, url, order,
						OrderResponse.class));

		OrderResponse or = (OrderResponse) hrespObj.getResponse(); 

		return or;
	}

	/**
	 * Retrieves information about a stock from the last trade.
	 * This is always information from the past!
	 * **/
	public Boolean getLastQuote(Order order) {
		System.out.println("Getting last quote");
		//construct the URL
		String url = Constants.baseURL +
				"/venues/" + order.getVenue() +
				"/stocks/" + order.getSymbol() +
				"/quote";

		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, order,
						LastQuote.class));

		LastQuote lq = (LastQuote) hrespObj.getResponse();

		System.out.println("Asks depth: " + lq.getAskDepth());
		System.out.println("Bids depth: " + lq.getBidDepth());

		return lq.getOk();
	}

	public Boolean getExistingOrderStatus(OrderResponse orderResp) {
		//construct the URL
		String url = Constants.baseURL +
				"/venues/" + orderResp.getVenue() +
				"/stocks/" + orderResp.getSymbol() +
				"/orders/" + orderResp.getId();

		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, orderResp,
						OrderResponse.class));

		OrderResponse or = (OrderResponse) hrespObj.getResponse();

		if(or.getOk()) {
			System.out.println("Still open: " + or.getOpen());
			System.out.println("Filled    : " + or.getTotalFills());
		}
		return or.getOk();
	}

	public Boolean cancelOrder(OrderResponse orderResp) {
		//construct the URL
		String url = Constants.baseURL + 
				"/venues/" + orderResp.getVenue() + 
				"/stocks/" + orderResp.getSymbol() + 
				"/orders/" + orderResp.getId() +
				"/cancel";

		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.POST, url, orderResp,
						OrderResponse.class));

		OrderResponse or = (OrderResponse) hrespObj.getResponse();

		if(or.getOk()) {
			System.out.println("Still open: " + or.getOpen());
			System.out.println("Filled    : " + or.getTotalFills());
		}

		return or.getOk();
	}

	/**
	 * Returns all the orders placed at the venue (optional - at stock level).
	 * Bit of a risk since the results are not paginated
	 *  - potentially a lot of incoming data
	 * **/
	public Boolean getStatusOfAllOrders(Order order) {
		//construct the URL
		String url =  Constants.baseURL +
				"/venues/" + order.getVenue() +
				"/accounts/" + order.getAccount() +
				(order.getSymbol().equals("") ? "" : "/stocks/" + order.getSymbol()) +
				"/orders"; 
		
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, order,
						OrderStatus.class));

		OrderStatus os = (OrderStatus) hrespObj.getResponse();
		
		System.out.println("Hot order count : " + os.getHotOrderCount());
		System.out.println("Cold order count: " + os.getColdOrderCount());
		
		return os.getOk();
	}
}
