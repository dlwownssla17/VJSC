package db;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import model.ModelTools;
import model.User;

public class UserDB extends ObjectDB {
	public static Document toDocument(User user) {
		return new Document("username", user.getUsername())
					.append("password", user.getPassword())
					.append("member-since", user.getMemberSince())
					.append("info", UserInfoDB.toDocument(user.getInfo()))
					.append("settings", UserSettingsDB.toDocument(user.getSettings()))
					.append("diabetes-params", UserDiabetesParamsDB.toDocument(user.getDiabetesParams()))
					.append("adherence-params", UserAdherenceParamsDB.toDocument(user.getAdherenceParams()))
					.append("community-params", UserCommunityParamsDB.toDocument(user.getCommunityParams()))
					.append("schedule", UserScheduleDB.toDocument(user.getSchedule()))
					.append("fitbit-account", FitBitAccountDB.toDocument(user.getFitBitAccount()));
	}
	
	public static User fromDocument(Document document) {
		User user = new User(document.getString("username"), document.getString("password"));
		
		user.setMemberSince(document.getDate("member-since"));
		user.setInfo(UserInfoDB.fromDocument((Document) document.get("info")));
		user.setSettings(UserSettingsDB.fromDocument((Document) document.get("settings")));
		user.setDiabetesParams(UserDiabetesParamsDB.fromDocument((Document) document.get("diabetes-params")));
		user.setAdherenceParams(UserAdherenceParamsDB.fromDocument((Document) document.get("adherence-params")));
		user.setCommunityParams(UserCommunityParamsDB.fromDocument((Document) document.get("community-params")));
		user.setSchedule(UserScheduleDB.fromDocument((Document) document.get("schedule")));
		user.setFitBitAccount(FitBitAccountDB.fromDocument((Document) document.get("fitbit-account")));
		
		return user;
	}
	
	public static User registerUser(String username, String password) {
		start();
		
		// user with username already exists
		if (users.count(eq("username", username)) > 0) return null;
		
		User newUser = new User(username, password);
		
		users.insertOne(toDocument(newUser));
		
		end();
		
		return newUser;
	}
	
	public static User findUser(String username) {
		start();
		
		Document existingUserDocument = users.find(eq("username", username)).first();
		
		end();
		
		return existingUserDocument == null ? null : fromDocument(existingUserDocument);
	}
	
	public static User loginUser(String username, String password) {
		User existingUser = findUser(username);
		if (existingUser == null || !existingUser.getPassword().equals(password)) return null;
		
		return existingUser;
	}
}
