package dataobjects;

import java.util.List;

public class GameData {

	private final String name;
	private final List<DailyInput> inputs;

	public GameData(String companyName, List<DailyInput> inputs) {
		this.name = companyName;
		this.inputs = inputs;
	}

	public String getName() {
		return name;
	}

	public List<DailyInput> getInputs() {
		return inputs;
	}

}
