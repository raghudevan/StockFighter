package sf.objects.queryresults;

import java.util.ArrayList;

public class OrderBookIndex extends APIResultObject {

	private ArrayList<Stock> symbols;
	
	public ArrayList<String> getSymbols() {
		
		ArrayList<String> symbolList = new ArrayList<String>();
		for(Stock stock : symbols) {
			symbolList.add(stock.getSymbol());
		}
		
		return symbolList;
	}

	public OrderBookIndex(Boolean ok, ArrayList<Stock> symbols) {
		super(ok);
		this.symbols = symbols;
	}

	public OrderBookIndex(Boolean ok, ArrayList<Stock> symbols,
			String error) {
		super(ok, error);
		this.symbols = symbols;
	}
}
