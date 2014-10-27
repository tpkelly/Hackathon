package game;

import trading.TradingStrategy;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;

public class TestImplementation {
	private static final int INITIAL_CAPITAL = 10000;

	public static void main(String[] args) throws GameFailureException {

		System.out.println("Profit breakdown:");
		int totalFunds = 0;

		for (String company : GameDataResolver.COMPANIES) {
			TradingManager tradingManager = new TradingManager(INITIAL_CAPITAL, 0);

			BaseTradingStrategy strategy = new TradingStrategy(tradingManager);
			GameData data = GameDataResolver.getInstance().getGameData(company);
			Game game = new Game(strategy, data);

			GameOutput output = game.getResult();

			int profit = output.getTotalFunds() - INITIAL_CAPITAL;
			System.out.println(company + ": £" + profit);
			totalFunds += output.getTotalFunds();
		}

		int totalProfit = totalFunds - GameDataResolver.COMPANIES.size() * INITIAL_CAPITAL;
		System.out.println("Total profit: £" + totalProfit);
		System.out.println("Final funds: £" + totalFunds);
	}
}
