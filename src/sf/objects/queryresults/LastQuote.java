package sf.objects.queryresults;

public class LastQuote extends APIResultObject {

	private String venue;
	private String symbol;
	private long bid;
	private long ask;
	private long last;
	private Integer bidSize;
	private Integer askSize;
	private Integer lastSize;
	private Integer bidDepth;
	private Integer askDepth;
	private String lastTrade;
	private String quoteTime;
	
	public String getVenue() {
		return venue;
	}

	public String getSymbol() {
		return symbol;
	}

	public long getBid() {
		return bid;
	}

	public long getAsk() {
		return ask;
	}

	public long getLast() {
		return last;
	}

	public Integer getBidSize() {
		return bidSize;
	}

	public Integer getAskSize() {
		return askSize;
	}

	public Integer getLastSize() {
		return lastSize;
	}

	public Integer getBidDepth() {
		return bidDepth;
	}

	public Integer getAskDepth() {
		return askDepth;
	}

	public String getLastTrade() {
		return lastTrade;
	}

	public String getQuoteTime() {
		return quoteTime;
	}

	public LastQuote(Boolean ok, String venue, String symbol,
			long bid, long ask, long last, Integer bidSize,
			Integer askSize, Integer lastSize, Integer bidDepth,
			Integer askDepth, String lastTrade, String quoteTime) {
		super(ok);
		this.venue = venue;
		this.symbol = symbol;
		this.bid = bid;
		this.ask = ask;
		this.last = last;
		this.bidSize = bidSize;
		this.askSize = askSize;
		this.lastSize = lastSize;
		this.bidDepth = bidDepth;
		this.askDepth = askDepth;
		this.lastTrade = lastTrade;
		this.quoteTime = quoteTime;
	}

	public LastQuote(Boolean ok) {
		super(ok);
		// TODO Auto-generated constructor stub
	}

	public LastQuote(Boolean ok, String error) {
		super(ok, error);
		// TODO Auto-generated constructor stub
	}

}
