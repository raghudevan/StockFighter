package sf.objects.queryresults;

import java.util.ArrayList;

public class OrderStatus extends APIResultObject {

	private String venue;
	private ArrayList<OrderResponse> orders; 
	private ArrayList<OrderResponse> hotOrders;
	private ArrayList<OrderResponse> coldOrders;
	
	public String getVenue() {
		return venue;
	}

	public ArrayList<OrderResponse> getOrders() {
		return orders;
	}
	
	public Integer getOrderCount() {
		
		if(hotOrders == null || coldOrders == null) {
			iterateThroughOrdersToRetrieveInfo();
		}
		return orders != null ? orders.size() : 0;
	}
	
	public Integer getHotOrderCount() {
		
		if(hotOrders == null || coldOrders == null) {
			iterateThroughOrdersToRetrieveInfo();
		}
		return hotOrders != null ? hotOrders.size() : 0;
	}
	
	public Integer getColdOrderCount() {
		return coldOrders != null ? coldOrders.size() : 0;
	}

	public OrderStatus(Boolean ok, String venue,
			ArrayList<OrderResponse> orders) {
		super(ok);
		this.venue = venue;
		this.orders = orders;
	}
	
	private void iterateThroughOrdersToRetrieveInfo() {
		hotOrders = new ArrayList<OrderResponse>();
		coldOrders = new ArrayList<OrderResponse>();
		for(OrderResponse order : orders) {
			if(order.getOpen()) {
				hotOrders.add(order);
			} else {
				coldOrders.add(order);
			}
		}
	}
	
	public OrderStatus(Boolean ok, String error) {
		super(ok, error);
	}

}
