package game;

import java.util.Iterator;
import java.util.LinkedList;

import tradingstrategy.BaseTradingStrategy;
import dataobjects.DailyInput;
import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;
import exceptions.InsufficientFundsException;
import exceptions.InsufficientSharesException;

public class Game {

	private final BaseTradingStrategy strategy;
	private final GameData gameData;

	public Game(BaseTradingStrategy strategy, GameData data) {
		this.strategy = strategy;
		this.gameData = data;
	}

	public GameOutput getResult() throws GameFailureException {
		LinkedList<DailyOutput> dailyOutputs = new LinkedList<DailyOutput>();
		for (Iterator<DailyInput> iterator = gameData.getInputs().iterator(); iterator.hasNext();) {
			DailyInput input = iterator.next();
			try {
				dailyOutputs.add(strategy.makeDailyTrade(input));
			} catch (InsufficientFundsException e) {
				throw new GameFailureException("You tried to make a trade but had insufficient funds. This occurred on day " + input.getDay(), e);
			} catch (InsufficientSharesException e) {
				throw new GameFailureException("You tried to make a trade but had insufficient shares. This occurred on day " + input.getDay(), e);
			}
		}

		DailyOutput last = (DailyOutput) dailyOutputs.getLast();
		int totalFunds = last.getInvestmentAmount() + last.getAvailableFunds();
		return new GameOutput(dailyOutputs, totalFunds);
	}

}
