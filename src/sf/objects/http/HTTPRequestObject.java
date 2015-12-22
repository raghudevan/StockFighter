package sf.objects.http;

import sf.utilities.http.HTTPManager.REQUEST_TYPE;

public class HTTPRequestObject 
{
	private REQUEST_TYPE rt;
	private String url;
	private Order parameters;
	private Class<?> returnClass;
	
	public HTTPRequestObject(REQUEST_TYPE requestType, String url,
			Order parameters) 
	{
		this.rt = requestType;
		this.url = url;
		this.parameters = parameters;
	}

	public HTTPRequestObject(REQUEST_TYPE requestType, String url,
			Order parameters, Class<?> returnClass) 
	{
		this.rt = requestType;
		this.url = url;
		this.parameters = parameters;
		this.returnClass = returnClass;
	}

	public REQUEST_TYPE getRequestType() {
		return rt;
	}

	public String getUrl() {
		return url;
	}

	public Order getParameters() {
		return parameters;
	}

	public Class<?> getReturnClass() {
		return returnClass;
	}

	public static class Order
	{
		private String account;
		private String venue;
		private String symbol;
		private Integer price;
		private Integer quantity;
		private String direction;
		private String orderType;

		//limit, market, fill-or-kill, immediate-or-cancel
		public enum ORDER_TYPE {LIMIT, MARKET, FOK, IOC};

		/*No order*/
		public Order() {}

		/*Get order book*/
		public Order(String venue, String symbol)
		{
			this.venue = venue;
			this.symbol = symbol;
		}

		/*Place an order*/
		public Order(String account, String venue,
				String symbol, Integer price,
				Integer quantity, String direction,
				ORDER_TYPE orderType) 
		{
			this.account = account;
			this.venue = venue;
			this.symbol = symbol;
			this.price = price;
			this.quantity = quantity;
			this.direction = direction;

			switch(orderType)
			{
			case LIMIT:
				this.orderType = "limit";
				break;
			case MARKET:
				this.orderType = "market";
				break;
			case FOK:
				this.orderType = "fill-or-kill";
				break;
			case IOC:
				this.orderType = "immediate-or-cancel";
				break;
			default:
				this.orderType = "limit";
				break;
			}
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
		public Integer getPrice() {
			return price;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public String getDirection() {
			return direction;
		}
		public String getOrderType() {
			return orderType;
		}
	}
}
