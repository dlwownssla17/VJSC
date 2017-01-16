package db;

import org.json.JSONObject;

import fitbit.FitBitTools;
import model.Team;
import model.User;

public class DBTools {
	public static UserDB toDBObj(User user) {
		UserDB userDB = new UserDB(user.getUsername(), user.getPassword(), user.getMemberSince());
		
		JSONObject fitBitAccountProfileJSON = new JSONObject(FitBitTools.getProfile(user.getFitBitAccount()));
		String fitBitDisplayName = fitBitAccountProfileJSON.getJSONObject("user").getString("displayName");
		userDB.setFitBitDisplayName(fitBitDisplayName);
		
		return userDB;
	}
	
	public static TeamDB toDBObj(Team team) {
		TeamDB teamDB = new TeamDB(team.getTeamName());
		
		for (User user : team.getUsers()) {
			teamDB.addUsername(user.getUsername());
		}
		
		return teamDB;
	}
}
