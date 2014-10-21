package dataobjects;

public class TradeActivity {

	private final int buy;
	private final int sell;

	public TradeActivity(int buy, int sell) {
		this.buy = buy;
		this.sell = sell;
	}

	public int getBuy() {
		return buy;
	}

	public int getSell() {
		return sell;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeActivity other = (TradeActivity) obj;
		if (buy != other.buy)
			return false;
		return sell == other.sell;
	}

	public String toString() {
		return (new StringBuilder("TradeActivity [buy=")).append(buy).append(", sell=").append(sell).append("]").toString();
	}
}