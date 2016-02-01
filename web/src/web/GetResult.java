package web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import dataobjects.GameOutput;
import exceptions.GameFailureException;
import game.DailyOutput;
import game.Game;
import game.TradingManager;
import trading.TradingStrategy;

public class GetResult extends HttpServlet {

	private static final long serialVersionUID = 1;
	private static final int INITIAL_CAPITAL = 30000;
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TradingManager tradingManager = new TradingManager(INITIAL_CAPITAL);
		TradingStrategy strategy = new TradingStrategy(tradingManager);

		Game game = new Game(strategy);
		GameOutput result;
		try {
			result = game.getResult();
		} catch (GameFailureException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return;
		}

		Result toReturn = new Result();
		toReturn.output = result;
		toReturn.chartData = buildChartData(result, Arrays.asList("Amazon", "Yahoo", "Tesco"));

		mapper.writeValue(resp.getOutputStream(), toReturn);
		resp.getOutputStream().close();
	}

	private List<Double> dataForCompany(String company) {
		List<Double> data = new LinkedList<Double>();
		
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("data/" + company + ".json");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		try {
			jsonNode = mapper.readTree(resourceAsStream);
		}
		catch (Exception e) {
			return data;
		}
		
		JsonNode results = jsonNode.get("query").get("results").get("quote");
		
		for (int i = 0; i < results.size(); i++) {
			JsonNode result = results.get(i);
			double close = Double.parseDouble(result.get("Close").getTextValue());
			data.add(close);
		}
		
		return data;
	}
	
	private List<ChartSeries> buildChartData(GameOutput result, List<String> companies) {
		List<ChartSeries> chartData = new LinkedList<ChartSeries>();

		for (String company : companies) {
			ChartSeries in = new ChartSeries();
			in.name = company +  " Close Price";
			in.data = dataForCompany(company);
			in.yAxis = 0;
			chartData.add(in);
		}

		ChartSeries out = new ChartSeries();
		out.name = "Total";
		out.data = new LinkedList<Double>();
		out.yAxis = 1;
		for (DailyOutput dailyOut : result.getDailyOutputs()) {
			out.data.add((double) (dailyOut.getAvailableFunds() + dailyOut.getInvestmentAmount()));
		}

		chartData.add(out);

		return chartData;
	}

	private class Result {
		private GameOutput output;
		private List<ChartSeries> chartData;
		private String company;

		public GameOutput getOutput() {
			return output;
		}

		public void setOutput(GameOutput output) {
			this.output = output;
		}

		public List<ChartSeries> getChartData() {
			return chartData;
		}

		public void setChartData(List<ChartSeries> chartData) {
			this.chartData = chartData;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

	}

	private class ChartSeries {
		private String name;
		private List<Double> data;
		private int yAxis;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Double> getData() {
			return data;
		}

		public void setData(List<Double> data) {
			this.data = data;
		}

		public int getyAxis() {
			return yAxis;
		}

		public void setyAxis(int yAxis) {
			this.yAxis = yAxis;
		}
	}
}
