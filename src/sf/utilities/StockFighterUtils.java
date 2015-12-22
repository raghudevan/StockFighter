package sf.utilities;

import java.util.ArrayList;

import sf.constants.Constants;
import sf.objects.http.HTTPRequestObject;
import sf.objects.http.HTTPRequestObject.Order;
import sf.objects.http.HTTPResponseObject;
import sf.objects.queryresults.APIResultObject;
import sf.objects.queryresults.OrderBookIndex;
import sf.objects.queryresults.OrderPage;
import sf.utilities.http.HTTPManager;

public class StockFighterUtils extends HTTPManager 
{
	private static StockFighterUtils _instance;
	public static StockFighterUtils getInstance()
	{
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
	public Boolean isAPIAlive()
	{
		System.out.println("Getting API status...");
		return performCheck(checkAPIHeartbeat_URL);
	}

	/**
	 * Defaults to checking the status of TESTEX
	 * **/
	public Boolean isVenueAlive()
	{
		return isVenueAlive("TESTEX");
	}

	/**
	 * Checks and returns the status of the venue specified by venueName
	 * **/
	public Boolean isVenueAlive(String venueName)
	{
		System.out.println("Checking for " + venueName + " status...");
		return performCheck(checkVenueHeartbeat_URL.replace("TESTEX",
				venueName));
	}

	private Boolean performCheck(String checkURL)
	{
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, checkURL, 
						new Order(), APIResultObject.class));

		APIResultObject aro = (APIResultObject) hrespObj.getResponse();
		return aro.getOk();
	}

	public ArrayList<String> listStocks(String venueName)
	{
		System.out.println("Listing stocks at " + venueName + ": ");

		String url = listStocks_URL.replace("OGEX", venueName);
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, 
						new Order(), OrderBookIndex.class));

		OrderBookIndex obi = (OrderBookIndex) hrespObj.getResponse();
		return obi.getSymbols();
	}

	/**
	 * Places an order as specified by the input parameter order
	 * OrderType: limit, market, fill-or-kill, immediate-or-cancel
	 * **/
	public void placeOrder(Order order)
	{
		//construct the URL
		String url = 
				Constants.baseURL + "/venues/" + 
						order.getVenue() + "/stocks/" +
						order.getSymbol() + "/orders";

		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.POST, url, order));

		if(hrespObj != null)
		{
			System.out.println(hrespObj.getResponse());
		}
	}
	
	/**
	 * Returns the current status of the a 
	 * stock at a particular venue
	 * **/
	public void getOrderBook(Order order)
	{
		String url =
				Constants.baseURL + "/venues/" +
						order.getVenue() + "/stocks/" +
						order.getSymbol();
		
		HTTPResponseObject hrespObj =
				makeRequest(new HTTPRequestObject(REQUEST_TYPE.GET, url, order, OrderPage.class));
		
		OrderPage op = (OrderPage) hrespObj.getResponse();
		
		if(hrespObj != null)
		{
			System.out.println(hrespObj.getResponse());
		}
	}
	

}
