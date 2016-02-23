package sf.objects.queryresults;

import java.util.ArrayList;

public class OrderResponse extends Order {

	private ORDER_TYPE type;
	private Integer originalQty;
	private Integer id;
	private String ts;
	private ArrayList<StockFill> fills;
	private Integer totalFilled;
	private Boolean open;
	
	public ORDER_TYPE getType() {
		return type;
	}
	
	public Integer getOriginalQty() {
		return originalQty;
	}

	public Integer getId() {
		return id;
	}

	public String getTs() {
		return ts;
	}

	public ArrayList<StockFill> getFills() {
		return fills;
	}

	public Integer getTotalFills() {
		return totalFilled;
	}

	public Boolean getOpen() {
		return open;
	}

	public OrderResponse(Boolean ok, String error) {
		super(ok, error);
	}
	
	public OrderResponse(Boolean ok, 
			String venue, String symbol, DIRECTION direction, 
			String account, long price, Integer quantity, ORDER_TYPE type,
			Integer originalQty, Integer id, String ts, ArrayList<StockFill> fills,
			Integer totalFills, Boolean open) {
		
		//TODO: is it possible for orderType != type;
		super(venue, symbol, direction, account, price, quantity, type);
		this.type = type;
		this.originalQty = originalQty;
		this.id = id;
		this.ts = ts;
		this.fills = fills;
		this.totalFilled = totalFills;
		this.open = open;
	}
}
