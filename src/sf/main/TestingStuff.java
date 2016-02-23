package sf.main;
import sf.objects.queryresults.Order;
import sf.objects.queryresults.Order.DIRECTION;
import sf.objects.queryresults.Order.ORDER_TYPE;
import sf.objects.queryresults.OrderResponse;
import sf.utilities.StockFighterUtils;
import sf.utilities.websocket.StockFighterWS;

public class TestingStuff {

	//TODO: Need to convert this into junit test cases
	public TestingStuff() {
		
		String tickerTape = "wss://api.stockfighter.io/ob/api/ws/EXB123456/venues/TESTEX/tickertape";
		StockFighterWS sfws = new StockFighterWS(tickerTape);
		sfws.startListening("ListeningToWebsockerActivity", false);
		
		testBuyingAndSelling();
	}
	
	public void testBuyingAndSelling() {
		StockFighterUtils sfUtils = StockFighterUtils.getInstance();
		Order o;
		OrderResponse or;
		
		o = new Order("TESTEX", "FOOBAR");
		sfUtils.getLastQuote(o);
		System.out.println("--------------------");
		
		Boolean shouldSell = Boolean.TRUE;
		if(shouldSell) {
			o = new Order("TESTEX", "FOOBAR", DIRECTION.sell,
					"EXB123456", 2500000, 10, ORDER_TYPE.fok);
			or = sfUtils.placeOrder(o);
			System.out.println("Order ID: " + or.getId());
			//sfUtils.getExistingOrderStatus(or);
			System.out.println("--------------------");
			
//			sfUtils.cancelOrder(or);
//			System.out.println("--------------------");
		}
		
		Boolean shouldBuy = Boolean.TRUE;
		if(shouldBuy) {
			o = new Order("TESTEX", "FOOBAR", DIRECTION.buy,
					"EXB123456", 0, 11, ORDER_TYPE.ioc);
			or = sfUtils.placeOrder(o);
			System.out.println("Order ID: " + or.getId());
			//sfUtils.getExistingOrderStatus(or);
			System.out.println("--------------------");
		}
		
		o = new Order("TESTEX", "FOOBAR", 
				DIRECTION.buy, Boolean.TRUE);
		o = sfUtils.getBestQuote(o);
		System.out.println("Best price: " + o.getPrice());
		System.out.println("Qty: " + o.getQuantity());
		
		o.setShouldRehash(Boolean.FALSE);
		o.setDirection(DIRECTION.sell);
		o = sfUtils.getBestQuote(o);
		System.out.println("Best price: " + o.getPrice());
		System.out.println("Qty: " + o.getQuantity());
		System.out.println("--------------------");
		
		o = new Order("TESTEX", "FOOBAR", "EXB123456"); //get all orders
		sfUtils.getStatusOfAllOrders(o);
		System.out.println("--------------------");
	}
	
	public void isStuffAlive() {
		StockFighterUtils sfUtils = StockFighterUtils.getInstance();
		
		System.out.println(sfUtils.isAPIAlive());
		System.out.println("--------------------");
		
		System.out.println(sfUtils.isVenueAlive());
		System.out.println("--------------------");
		
		System.out.println(sfUtils.listStocks("TESTEX"));
		System.out.println("--------------------");
	}
}
