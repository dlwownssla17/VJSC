package db;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import fitbit.FitBitAccount;
import fitbit.FitBitTools;
import model.Team;
import model.User;
import util.DateFormat;
import util.IO;

public class DBTools {
	private static String DB = "db";
	
	protected static String MemberSinceDateTimeFormat = "yyyy-MM-dd HH:mm";
	
	public static UserDB toUserDB(User user) {
		UserDB userDB = new UserDB(user.getUsername(), user.getPassword(), user.getMemberSince());
		
		if (user.hasFitBitAccount()) {
			userDB.setFitBitAccessToken(user.getFitBitAccount().getAccessToken());
			userDB.setFitBitRefreshToken(user.getFitBitAccount().getRefreshToken());
			userDB.setFitBitUserId(user.getFitBitAccount().getUserId());
			userDB.setFitBitScope(user.getFitBitAccount().getScope());
			userDB.setFitBitTokenType(user.getFitBitAccount().getTokenType());
			userDB.setFitBitExpiresIn(Long.toString(user.getFitBitAccount().getExpiresIn()));
			
			JSONObject fitBitAccountProfileJSON = new JSONObject(FitBitTools.getProfile(user.getFitBitAccount()));
			String fitBitDisplayName = fitBitAccountProfileJSON.getJSONObject("user").getString("displayName");
			userDB.setFitBitDisplayName(fitBitDisplayName);
		}
		
		return userDB;
	}
	
	public static TeamDB toTeamDB(Team team) {
		TeamDB teamDB = new TeamDB(team.getTeamName());
		
		for (User user : team.getUsers()) {
			teamDB.addUsername(user.getUsername());
		}
		
		return teamDB;
	}
	
	public static User fromUserDB(UserDB userDB) {
		try {
			User user = new User(userDB.getUsername(), userDB.getPassword());
			
			Date memberSince = DateFormat.getDate(userDB.getMemberSince(), MemberSinceDateTimeFormat);
			user.setMemberSince(memberSince);
			
			String fitBitAccessToken = userDB.getFitBitAccessToken();
			String fitBitRefreshToken = userDB.getFitBitRefreshToken();
			String fitBitUserId = userDB.getFitBitUserId();
			String fitBitScope = userDB.getFitBitScope();
			String fitBitTokenType = userDB.getFitBitTokenType();
			long fitBitExpiresIn = Long.parseLong(userDB.getFitBitExpiresIn());
			
			FitBitAccount fitBitAccount = new FitBitAccount(fitBitAccessToken, fitBitRefreshToken, fitBitUserId,
					fitBitScope, fitBitTokenType, fitBitExpiresIn);
			user.linkFitBitAccount(fitBitAccount);
			
			return user;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO fromTeamDB
	
	public static JSONObject toJSON(UserDB userDB) {
		JSONObject userJSON = new JSONObject();
		userJSON.put("username", userDB.getUsername());
		userJSON.put("password",  userDB.getPassword());
		userJSON.put("member_since", userDB.getMemberSince());
		userJSON.put("fitbit_access_token", userDB.getFitBitAccessToken());
		userJSON.put("fitbit_refresh_token", userDB.getFitBitRefreshToken());
		userJSON.put("fitbit_user_id", userDB.getFitBitUserId());
		userJSON.put("fitbit_scope", userDB.getFitBitScope());
		userJSON.put("fitbit_token_type", userDB.getFitBitTokenType());
		userJSON.put("fitbit_expires_in", userDB.getFitBitExpiresIn());
		userJSON.put("fitbit_display_name", userDB.getFitBitDisplayName());
		return userJSON;
	}
	
	public static JSONObject toJSON(TeamDB teamDB) {
		JSONObject teamJSON = new JSONObject();
		teamJSON.put("team_name", teamDB.getTeamName());
		teamJSON.put("usernames", new JSONArray(teamDB.getUsernames()));
		return teamJSON;
	}
	
	public static UserDB toUserDB(JSONObject userDBJSON) {
		String username = userDBJSON.getString("username");
		String password = userDBJSON.getString("password");
		String memberSince = userDBJSON.getString("member_since");
		String fitBitAccessToken = userDBJSON.getString("fitbit_access_token");
		String fitBitRefreshToken = userDBJSON.getString("fitbit_refresh_token");
		String fitBitUserId = userDBJSON.getString("fitbit_user_id");
		String fitBitScope = userDBJSON.getString("fitbit_scope");
		String fitBitTokenType = userDBJSON.getString("fitbit_token_type");
		String fitBitExpiresIn = userDBJSON.getString("fitbit_expires_in");
		String fitBitDisplayName = userDBJSON.getString("fitbit_display_name");
		
		UserDB userDB = new UserDB(username, password, memberSince);
		userDB.setFitBitAccessToken(fitBitAccessToken);
		userDB.setFitBitRefreshToken(fitBitRefreshToken);
		userDB.setFitBitUserId(fitBitUserId);
		userDB.setFitBitScope(fitBitScope);
		userDB.setFitBitTokenType(fitBitTokenType);
		userDB.setFitBitExpiresIn(fitBitExpiresIn);
		userDB.setFitBitDisplayName(fitBitDisplayName);
		return userDB;
	}
	
	public static TeamDB toTeamDB(JSONObject teamDBJSON) {
		String teamName = teamDBJSON.getString("team_name");
		JSONArray usernames = teamDBJSON.getJSONArray("usernames");
		
		TeamDB teamDB = new TeamDB(teamName);
		for (int i = 0; i < usernames.length(); i++) {
			teamDB.addUsername(usernames.getString(i));
		}
		return teamDB;
	}
	
	protected static String printDB() {
		try {
			return IO.readFile(DB);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static JSONObject loadDB() {
		File db = new File(DB);
		
		if (db.exists()) return new JSONObject(printDB());
		
		JSONObject newDB = new JSONObject();
		newDB.put("users", new JSONObject());
		newDB.put("teams", new JSONObject());
		return newDB;
	}
	
	private static void saveDB(JSONObject db) {
		try {
			IO.writeFile(DB, db.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO these methods are gonna have to become more granular
	
	public static UserDB getUserDB(String username) {
		JSONObject db = loadDB();
		
		JSONObject userDBJSON = db.getJSONObject("users").getJSONObject(username);
		
		return toUserDB(userDBJSON);
	}
	
	public static boolean setUserDB(UserDB userDB) {
		JSONObject db = loadDB();
		
		db.getJSONObject("users").put(userDB.getUsername(), toJSON(userDB));
		saveDB(db);
		
		return true;
	}
	
	public static boolean removeUserDB(UserDB userDB) {
		JSONObject db = loadDB();
		
		db.getJSONObject("users").remove(userDB.getUsername());
		saveDB(db);
		
		return true;
	}
	
	public static TeamDB getTeamDB(String teamName) {
		JSONObject db = loadDB();
		
		JSONObject teamDBJSON = db.getJSONObject("teams").getJSONObject(teamName);
		
		return toTeamDB(teamDBJSON);
	}
	
	public static boolean setTeamDB(TeamDB teamDB) {
		JSONObject db = loadDB();
		
		db.getJSONObject("teams").put(teamDB.getTeamName(), toJSON(teamDB));
		saveDB(db);
		
		return true;
	}
	
	public static boolean removeTeamDB(TeamDB teamDB) {
		JSONObject db = loadDB();
		
		db.getJSONObject("teams").remove(teamDB.getTeamName());
		saveDB(db);
		
		return true;
	}
}
