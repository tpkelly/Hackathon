import dataobjects.GameData;
import dataobjects.GameOutput;
import exceptions.GameFailureException;
import trading.TradingStrategy;
import tradingstrategy.BaseTradingStrategy;
import game.DailyOutput;
import game.Game;
import game.GameDataResolver;


public class TestImplementation {

	public static void main (String[] args) throws GameFailureException {

		System.out.println("Companies available to trade on are: " + GameDataResolver.COMPANIES);
		
		//change this to test different companies
		String company = "Netflix";
		
		System.out.println("Starting trading on " + company);
		
		BaseTradingStrategy strategy = new TradingStrategy();
		GameData data = GameDataResolver.getInstance().getGameData(company);
		Game game = new Game(strategy, data);
		
		GameOutput output = game.getResult();
		
		System.out.println("Total funds made: £" + output.getTotalFunds());
		System.out.println("Detailed daily output:");
		for (DailyOutput out : output.getDailyOutputs()) {
			System.out.println(out);
		}
	}
}
