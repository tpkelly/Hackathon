package game;

import trading.TradingStrategy;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;

public class TestImplementation {
	private static final int INITIAL_CAPITAL = 10000;

	public static void main(String[] args) throws GameFailureException {
		TradingManager tradingManager = new TradingManager(INITIAL_CAPITAL, 0);

		BaseTradingStrategy strategy = new TradingStrategy(tradingManager);
		GameData data = GameDataResolver.getInstance().getGameData();
		Game game = new Game(strategy, data);

		GameOutput output = game.getResult();
		System.out.println("Final funds: £" + output.getTotalFunds());
	}
}
