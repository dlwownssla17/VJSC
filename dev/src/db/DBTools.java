package db;

import model.User;
import model.Team;

public class DBTools {
	public static UserDB toDBObj(User user) {
		return null;
	}
	
	public static TeamDB toDBObj(Team team) {
		TeamDB teamDB = new TeamDB(team.getTeamName());
		for (User user : team.getUsers()) {
			teamDB.addUsername(user.getUsername());
		}
		return teamDB;
	}
}
