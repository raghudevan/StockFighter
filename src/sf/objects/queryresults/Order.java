package sf.objects.queryresults;

public class Order extends APIResultObject {

	private String account;
	private String venue;
	private String symbol;
	private long price;
	private Integer qty;
	private DIRECTION direction;
	private ORDER_TYPE orderType;

	private Boolean shouldRehash = Boolean.FALSE;

	//limit, market, fill-or-kill, immediate-or-cancel
	public enum ORDER_TYPE {limit, market, fok, ioc};

	public enum DIRECTION {buy, sell};

	/*No order*/
	public Order() {
		super(Boolean.TRUE);
	}

	/*Get order book*/
	public Order(String venue, String symbol,
			DIRECTION direction, Boolean shouldRehash) {
		super(Boolean.TRUE);
		this.venue = venue;
		this.symbol = symbol;
		this.direction = direction;
		this.shouldRehash = shouldRehash;
	}

	/*Place an order*/
	public Order(String venue, String symbol,
			DIRECTION direction, String account, 
			long price, Integer quantity, 
			ORDER_TYPE orderType) {
		super(Boolean.TRUE);
		this.account = account;
		this.venue = venue;
		this.symbol = symbol;
		this.price = price;
		this.qty = quantity;
		this.direction = direction;
		this.orderType = orderType;
	}
	
	/*Get last quote*/
	public Order(String venue, String symbol) {
		super(Boolean.TRUE);
		this.venue = venue;
		this.symbol = symbol;
	}
	
	/*Get status of orders*/
	public Order(String venue, String symbol, String account) {
		super(Boolean.TRUE);
		this.venue = venue;
		this.symbol = symbol;
		this.account = account;
	}

	/*In the context of a response*/
	protected Order(Boolean ok, String error) {
		super(ok, error);
	}

	public String getAccount() {
		return account;
	}

	public String getVenue() {
		return venue;
	}

	public String getSymbol() {
		return symbol;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return qty;
	}

	public void setQuantity(Integer quantity) {
		this.qty = quantity;
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public ORDER_TYPE getOrderType() {
		return orderType;
	}

	public void setShouldRehash(Boolean shouldRehash) {
		this.shouldRehash = shouldRehash;
	}

	public Boolean getShouldRehash() {
		return shouldRehash;
	}
}
