package dataobjects;

public class DailyInput {

	private int day;
	private int remainingDays;
	private double open;
	private double close;
	private double high;
	private double low;

	public DailyInput(int day, int remainingDays, double open, double close, double high, double low) {
		this.day = day;
		this.remainingDays = remainingDays;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}

	public int getDay() {
		return day;
	}

	public int getRemainingDays() {
		return remainingDays;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

}