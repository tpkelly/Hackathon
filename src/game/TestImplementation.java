package game;

import trading.TradingStrategy;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;

public class TestImplementation {
	static final int initialFunds = 10000;

	public static void main(String[] args) throws GameFailureException {
		
		System.out.println("Profit breakdown:");
		int totalRevenue = 0;
		
		for (String company : GameDataResolver.COMPANIES)
		{
			TradingManager tradingManager = new TradingManager(initialFunds, 0);

			BaseTradingStrategy strategy = new TradingStrategy(tradingManager);
			GameData data = GameDataResolver.getInstance().getGameData(company);
			Game game = new Game(strategy, data);
	
			GameOutput output = game.getResult();
	
			System.out.println(company + ": £" + output.getTotalFunds());
			totalRevenue += output.getTotalFunds();
		}
		
		System.out.println("Total revenue: £" + totalRevenue);
	}
}
