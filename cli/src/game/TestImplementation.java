package game;

import trading.TradingStrategy;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;

public class TestImplementation {
	private static final int INITIAL_CAPITAL = 30000;

	public static void main(String[] args) throws GameFailureException {
		TradingManager tradingManager = new TradingManager(INITIAL_CAPITAL);

		BaseTradingStrategy strategy = new TradingStrategy(tradingManager);
		Game game = new Game(strategy);

		GameOutput output = game.getResult();
		System.out.println("Final funds: £" + output.getTotalFunds());
	}
}
