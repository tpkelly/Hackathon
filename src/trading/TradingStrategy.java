package trading;

import game.TradingManager;
import tradingstrategy.BaseTradingStrategy;
import dataobjects.DailyInput;
import dataobjects.DailyTrades;
import exceptions.InsufficientFundsException;
import exceptions.InsufficientSharesException;

public class TradingStrategy extends BaseTradingStrategy {

	public TradingStrategy(TradingManager tradingManager) {
		super.tradingManager = tradingManager;
	}

	@Override
	public void makeDailyTrade(DailyTrades trades) throws InsufficientFundsException, InsufficientSharesException {
		//use the trading manager to make trades based on input
		
		for (DailyInput input : trades.getTrades()) {
			if (trades.getDay() % 2 == 1) {
				tradingManager.buyMaxNumberOfShares(input);
			} else {
				tradingManager.sellAllShares(input);
			}
		}
	}
}
