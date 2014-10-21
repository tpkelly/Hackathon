package dataobjects;

import game.DailyOutput;

import java.util.List;

public class GameOutput {

	private final List<DailyOutput> dailyOutputs;
	private final int totalFunds;

	public GameOutput(List<DailyOutput> dailyOutputs, int totalFunds) {
		this.dailyOutputs = dailyOutputs;
		this.totalFunds = totalFunds;
	}

	public List<DailyOutput> getDailyOutputs() {
		return dailyOutputs;
	}

	public int getTotalFunds() {
		return totalFunds;
	}

}
