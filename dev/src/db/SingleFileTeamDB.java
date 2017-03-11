package db;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SingleFileTeamDB {
	private String teamName;
	private ArrayList<String> usernames;
	
	public SingleFileTeamDB(String teamName) {
		this.teamName = teamName;
		this.usernames = new ArrayList<>();
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public boolean addUsername(String username) {
		return this.usernames.add(username);
	}
	
	public boolean removeUsername(String username) {
		return this.usernames.remove(username);
	}
	
	public ArrayList<String> getUsernames() {
		return this.usernames;
	}
	
	public static void main(String[] args) {
		SingleFileTeamDB teamDB = new SingleFileTeamDB("Pokemon");
		teamDB.addUsername("whatsup");
		teamDB.addUsername("dlwownssla17");
		teamDB.addUsername("vjsc");
		teamDB.addUsername("spiro");
		teamDB.removeUsername("vjsc");
		System.out.println(SingleFileDBTools.toJSON(teamDB).toString(4));
	}
}
