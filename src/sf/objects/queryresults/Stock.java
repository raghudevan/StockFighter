package sf.objects.queryresults;

public class Stock {

	private String name;
	private String symbol;
	private String venue;
	
	private long price;
	private Integer qty;
	private Boolean isBuy;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getVenue() {
		return venue;
	}
	
	public void setVenue(String venue) {
		this.venue = venue;
	}

	public long getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return qty;
	}

	public Boolean getIsBuy() {
		return isBuy;
	}
	
	public Stock(String name, String symbol) 
	{
		this.name = name;
		this.symbol = symbol;
		
		this.venue = "";
		this.price = 0;
		this.qty = 0;
		this.isBuy = Boolean.FALSE;
	}
	
	public Stock(long price,
			Integer quantity,
			Boolean isBuy)
	{
		this.price = price;
		this.qty = quantity;
		this.isBuy = isBuy;
	}
}
