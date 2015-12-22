package sf.main;

import sf.objects.http.HTTPRequestObject.Order;
import sf.objects.http.HTTPRequestObject.Order.ORDER_TYPE;
import sf.utilities.StockFighterUtils;


public class Nexus {

	public static void main(String[] args) 
	{
		StockFighterUtils sfUtils = StockFighterUtils.getInstance();
		
		sfUtils.isAPIAlive();
		System.out.println("--------------------");
		
		sfUtils.isVenueAlive();
		System.out.println("--------------------");
		
		System.out.println(sfUtils.listStocks("TESTEX"));
		System.out.println("--------------------");
		
		Order o = new Order("account", "TESTEX", "FOOBAR",
				2500, 100, "buy", ORDER_TYPE.LIMIT);
		sfUtils.placeOrder(o);
		System.out.println("--------------------");
		
//		Order o = new Order("TESTEX", "FOOBAR");
//		sfUtils.getOrderBook(o);
//		System.out.println("--------------------");
		
	}
	

}
