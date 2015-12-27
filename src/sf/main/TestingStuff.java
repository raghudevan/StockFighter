package sf.main;
import sf.objects.queryresults.Order;
import sf.objects.queryresults.Order.DIRECTION;
import sf.objects.queryresults.Order.ORDER_TYPE;
import sf.utilities.StockFighterUtils;

public class TestingStuff {

	//TODO: Need to convert this into junit test cases
	public TestingStuff() {
		
		StockFighterUtils sfUtils = StockFighterUtils.getInstance();
		Order o;

		o = new Order("TESTEX", "FOOBAR");
		sfUtils.getLastQuote(o);
		System.out.println("--------------------");
		
		o = new Order("TESTEX", "FOOBAR", DIRECTION.sell,
				"EXB123456", 0, 1, ORDER_TYPE.limit);
		System.out.println(sfUtils.placeOrder(o));
		
		o = new Order("TESTEX", "FOOBAR", DIRECTION.buy,
				"EXB123456", 2500000, 3, ORDER_TYPE.limit);
		System.out.println(sfUtils.placeOrder(o));
		System.out.println("--------------------");
		
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
