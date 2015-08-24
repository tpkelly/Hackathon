package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import dataobjects.DailyTrades;
import dataobjects.GameData;
import game.GameDataResolver;

public class GetDailyInput extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		GameData gameData = GameDataResolver.getInstance().getGameData();			
		List<DailyTrades> inputs = gameData.getInputs();
		
		mapper.writeValue(resp.getOutputStream(), inputs);
		resp.getOutputStream().close();
	}

}
