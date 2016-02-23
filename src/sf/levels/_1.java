package sf.levels;

import sf.utilities.websocket.StockFighterWS;

public class _1 {
	
	private String account = "IEY59137130";
	private String exchange = "EKEHEX";
	private String stock = "EETR";

	public _1() {
		
		String tickerTape = "wss://api.stockfighter.io/ob/api/ws/" + account +
				"/venues/" + exchange + "/tickertape/stocks/" + stock;
		StockFighterWS sfws = new StockFighterWS(tickerTape);
		sfws.startListening("ListeningToWebsockerActivity", false);
		
//		StockFighterUtils sfUtils = StockFighterUtils.getInstance();
//		
//		Order o = new Order(exchange, stock, DIRECTION.buy,
//				account, 27, 100, ORDER_TYPE.limit);
//		sfUtils.placeOrder(o);
	}
}
