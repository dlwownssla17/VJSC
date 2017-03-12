package db;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.ScheduleItem;
import model.User;

public class DBTools {
	public static String HOST = "localhost";
	public static int PORT = 27017;
	public static String DATABASE_NAME = "debradb";
	public static String USERDB = "user";
	public static String TEAMDB = "team";
	
	public static MongoClient client;
	public static MongoDatabase database;
	public static MongoCollection<Document> users;
	public static MongoCollection<Document> teams;
	
	public static FitBitAccountDB fitBitAccountDB = new FitBitAccountDB();
	public static ProgressDB progressDB = new ProgressDB();
	public static ScheduleItemDB scheduleItemDB = new ScheduleItemDB();
	public static ScheduleItemNotificationParamsDB scheduleItemNotificationParamsDB = new ScheduleItemNotificationParamsDB();
	public static ScheduleItemRecurrenceDB scheduleItemRecurrenceDB = new ScheduleItemRecurrenceDB();
	public static TeamDB teamDB = new TeamDB();
	public static UserAdherenceParamsDB userAdherenceParamsDB = new UserAdherenceParamsDB();
	public static UserCommunityParamsDB userCommunityParamsDB = new UserCommunityParamsDB();
	public static UserDB userDB = new UserDB();
	public static UserDiabetesParamsDB userDiabetesParamsDB = new UserDiabetesParamsDB();
	public static UserInfoDB userInfoDB = new UserInfoDB();
	public static UserScheduleDB userScheduleDB = new UserScheduleDB();
	public static UserSettingsDB userSettingsDB = new UserSettingsDB();
	
	public static void open() {
		client = new MongoClient(HOST, PORT);
		database = client.getDatabase(DATABASE_NAME);
		
		users = database.getCollection(USERDB);
		teams = database.getCollection(TEAMDB);
	}
	
	public static void close() {
		client.close();
	}
	
	public static void init() {
		open();
		
		Document dummy = new Document();
		users.insertOne(dummy);
		users.deleteOne(dummy);
		teams.insertOne(dummy);
		teams.deleteOne(dummy);
		
		close();
	}
	
	// assume open connection to database
	public static User findUser(String username) {
		Document existingUserDocument = users.find(eq("username", username)).first();
		return existingUserDocument == null ? null : userDB.fromDocument(existingUserDocument);
	}
	
	public static User registerUser(String username, String password) {
		open();
		
		// user with username already exists
		if (users.count(eq("username", username)) > 0) return null;
		
		User newUser = new User(username, password);
		
		users.insertOne(userDB.toDocument(newUser));
		
		close();
		
		return newUser;
	}
	
	public static User loginUser(String username, String password) {
		open();
		
		User existingUser = findUser(username);
		
		close();
		
		return existingUser == null || !existingUser.getPassword().equals(password) ? null : existingUser;
	}
	
	public static ArrayList<ScheduleItem> findDailyItems(String username, Date date) {
		open();
		
		User existingUser = findUser(username);
		
		close();
		
		return existingUser == null ? null : existingUser.getSchedule().getItemsForDate(date);
	}
	
	public static int findDailyScore(String username, Date date) {
		open();
		
		User existingUser = findUser(username);
		
		close();
		
		return existingUser == null ? -1 : existingUser.getSchedule().computeDailyScore(date);
	}
	
	public static void updateUser(User user) {
		open();
		
		users.replaceOne(eq("username", user.getUsername()), userDB.toDocument(user));
		
		close();
	}
	
	public static void updateUserSchedule(User user) {
		open();
		
		Document newUserSchedule = new Document("schedule", userScheduleDB.toDocument(user.getSchedule()));
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserSchedule));
		
		close();
	}
	
	
	
	public static void main(String[] args) {
		init();
	}
}
