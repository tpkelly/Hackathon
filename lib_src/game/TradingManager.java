package game;

import dataobjects.DailyInput;
import dataobjects.TradeActivity;
import exceptions.InsufficientFundsException;
import exceptions.InsufficientSharesException;

public final class TradingManager {

	private int availableFunds;
	private int sharesOwned;

	public TradingManager() {
		availableFunds = 10000;
		sharesOwned = 0;
	}

	TradingManager(int availableFunds, int sharesOwned) {
		this.availableFunds = availableFunds;
		this.sharesOwned = sharesOwned;
	}

	public int getAvailableFunds() {
		return availableFunds;
	}

	public int getSharesOwned() {
		return sharesOwned;
	}

	public int getInvestmentAmount(DailyInput input) {
		return (int) ((double) sharesOwned * input.getClose());
	}

	/**
	 * Buys a number of shares worth the amount given based on the close price
	 * of the input
	 * 
	 * @param input
	 * @param value
	 * @return
	 * @throws InsufficientFundsException
	 *             if there are less funds available than the given value
	 */
	public DailyOutput buySharesOfValue(DailyInput input, int value) throws InsufficientFundsException {
		if (value < 0) {
			throw new IllegalArgumentException("Negative number given");
		}
		int sharesToBuy = (int) ((double) value / input.getClose());
		TradeActivity activity = new TradeActivity(sharesToBuy, 0);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientSharesException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Buys the given number of shares at the close price of the input
	 * 
	 * @param input
	 * @param totalShares
	 * @return
	 * @throws InsufficientFundsException
	 *             if there are insufficient funds to buy the number of shares
	 *             requested
	 */
	public DailyOutput buyNumberOfShares(DailyInput input, int totalShares) throws InsufficientFundsException {
		if (totalShares < 0) {
			throw new IllegalArgumentException("Negative number given");
		}
		TradeActivity activity = new TradeActivity(totalShares, 0);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientSharesException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Uses all of the available funds to buy as many shares as possible at the
	 * close price of the input. Note that after this trade, the available
	 * balance may not be zero as the available funds may not be exactly
	 * divisible by the close price.
	 * 
	 * @param input
	 * @return
	 */
	public DailyOutput buyMaxNumberOfShares(DailyInput input) {
		int sharesToBuy = (int) ((double) availableFunds / input.getClose());
		TradeActivity activity = new TradeActivity(sharesToBuy, 0);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientFundsException e) {
			throw new RuntimeException();
		} catch (InsufficientSharesException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Sells a number of shares worth the amount given based on the close price
	 * of the input.
	 * 
	 * @param input
	 * @param value
	 * @return
	 * @throws InsufficientSharesException
	 *             if there are insufficient shares to the value of the given
	 *             input
	 */
	public DailyOutput sellSharesOfValue(DailyInput input, int value) throws InsufficientSharesException {
		if (value < 0) {
			throw new IllegalArgumentException("Negative number given");
		}
		int sharesToSell = (int) ((double) value / input.getClose());
		TradeActivity activity = new TradeActivity(0, sharesToSell);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientFundsException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Sells the given number of shares at the close price of the input
	 * 
	 * @param input
	 * @param totalShares
	 * @return
	 * @throws InsufficientSharesException
	 *             if there are insufficient shares currently held to sell the
	 *             number of shares requested
	 */
	public DailyOutput sellNumberOfShares(DailyInput input, int totalShares) throws InsufficientSharesException {
		if (totalShares < 0) {
			throw new IllegalArgumentException("Negative number given");
		}
		TradeActivity activity = new TradeActivity(0, totalShares);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientFundsException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Sells all currently held shares
	 * 
	 * @param input
	 * @return
	 */
	public DailyOutput sellAllShares(DailyInput input) {
		TradeActivity activity = new TradeActivity(0, sharesOwned);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientFundsException e) {
			throw new RuntimeException();
		} catch (InsufficientSharesException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Complete no trade activity
	 * 
	 * @param input
	 * @return
	 */
	public DailyOutput doNothing(DailyInput input) {
		TradeActivity activity = new TradeActivity(0, 0);
		try {
			return makeTrade(input, activity);
		} catch (InsufficientFundsException e) {
			throw new RuntimeException();
		} catch (InsufficientSharesException e) {
			throw new RuntimeException();
		}
	}

	private DailyOutput makeTrade(DailyInput input, TradeActivity tradeActivity) throws InsufficientFundsException, InsufficientSharesException {
		sharesOwned += tradeActivity.getBuy();
		sharesOwned -= tradeActivity.getSell();
		if (sharesOwned < 0) {
			throw new InsufficientSharesException("You have insufficent shares to sell");
		}
		availableFunds += (double) tradeActivity.getSell() * input.getClose();
		availableFunds -= (double) tradeActivity.getBuy() * input.getClose();
		if (availableFunds < 0) {
			throw new InsufficientFundsException("You have insufficient funds to make this trade");
		} else {
			return new DailyOutput(tradeActivity, availableFunds, (int) ((double) sharesOwned * input.getClose()), input.getDay());
		}
	}

}
