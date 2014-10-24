package trading;

import game.DailyOutput;
import game.TradingManager;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.DailyInput;
import exceptions.InsufficientFundsException;
import exceptions.InsufficientSharesException;

public class TradingStrategy extends BaseTradingStrategy {

	public TradingStrategy(TradingManager tradingManager) {
		super.tradingManager = tradingManager;
	}

	@Override
	public DailyOutput makeDailyTrade(DailyInput input) throws InsufficientFundsException, InsufficientSharesException {
		// use the trading manager to make trades based on input
		DailyOutput output;
		if (input.getDay() % 2 == 0) {
			output = tradingManager.buyMaxNumberOfShares(input);
		} else {
			output = tradingManager.sellAllShares(input);
		}

		return output;
	}

}
