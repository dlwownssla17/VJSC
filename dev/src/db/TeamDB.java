package db;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class TeamDB {
	private String teamName;
	private ArrayList<String> usernames;
	
	public TeamDB(String teamName) {
		this.teamName = teamName;
		this.usernames = new ArrayList<>();
	}
	
	protected boolean addUsername(String username) {
		return this.usernames.add(username);
	}
	
	protected boolean removeUsername(String username) {
		return this.usernames.remove(username);
	}
	
	protected String toJSONString() {
		JSONObject teamJSON = new JSONObject();
		teamJSON.put("team_name", this.teamName);
		teamJSON.put("usernames", new JSONArray(this.usernames));
		return teamJSON.toString(4);
	}
	
	public static void main(String[] args) {
		TeamDB teamDB = new TeamDB("Pokemon");
		teamDB.addUsername("whatsup");
		teamDB.addUsername("dlwownssla17");
		teamDB.addUsername("vjsc");
		teamDB.addUsername("spiro");
		teamDB.removeUsername("vjsc");
		System.out.println(teamDB.toJSONString());
	}
}
