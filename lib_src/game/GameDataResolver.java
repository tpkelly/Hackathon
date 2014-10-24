package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import dataobjects.DailyInput;
import dataobjects.GameData;

public class GameDataResolver {

	private static GameDataResolver instance;
	private static Object LOCK = new Object();
	public static final List<String> COMPANIES = Arrays.asList((new File("data")).list());

	private final Map<String, GameData> data = new HashMap<String, GameData>();

	private GameDataResolver() {
		for (String company : COMPANIES) {
			try {
				data.put(company, createGameData(company));
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private GameData createGameData(String company) throws JsonParseException, IOException {
		InputStream resourceAsStream = new FileInputStream("data" + File.separator + company);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(resourceAsStream);
		JsonNode results = jsonNode;
		List<DailyInput> inputs = new LinkedList<DailyInput>();
		for (int i = 0; i < results.size(); i++) {
			JsonNode result = results.get(i);
			double open = Double.parseDouble(result.get("open").getTextValue());
			double close = Double.parseDouble(result.get("close").getTextValue());
			double high = Double.parseDouble(result.get("high").getTextValue());
			double low = Double.parseDouble(result.get("low").getTextValue());
			int day = i + 1;
			inputs.add(new DailyInput(day, results.size() - day, open, close, high, low));
		}

		return new GameData(company, inputs);
	}

	public GameData getGameData(String company) {
		GameData gameData = (GameData) data.get(company);
		if (gameData == null) {
			throw new IllegalArgumentException("supplied company is invalid");
		} else {
			return gameData;
		}
	}

	public static GameDataResolver getInstance() {
		synchronized (LOCK) {
			if (instance == null) {
				instance = new GameDataResolver();
			}
		}
		return instance;
	}

}
