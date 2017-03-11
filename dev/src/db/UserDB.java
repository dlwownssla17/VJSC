package db;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.ModelTools;

public class UserDB {
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> users;
	// private static MongoCollection<Document> teams;
	
	private static void start() {
		mongoClient = new MongoClient(DBTools.HOST, DBTools.PORT);
		database = mongoClient.getDatabase(DBTools.DATABASE);
		
		users = database.getCollection(DBTools.USERDB);
		// teams = database.getCollection(DBTools.TEAMDB);
	}
	
	private static void end() {
		mongoClient.close();
	}
	
	public static Document registerUser(String username, String password) {
		start();
		
		// user with username already exists
		if (users.count(eq("username", username)) > 0) return null;
		
		Document newUser = new Document("username", username)
							.append("password", password)
							.append("member-since", new Date())
							.append("info",
									new Document())
							.append("settings",
									new Document())
							.append("diabetes-params",
									new Document())
							.append("adherence-params",
									new Document())
							.append("community-params",
									new Document())
							.append("schedule",
									new Document("capacity", ModelTools.DEFAULT_CAPACITY)
										.append("schedule-id-counter", 0)
										.append("recurring-id-counter", 0)
										.append("schedule-dates", new ArrayList<Document>()))
							.append("fitbit-account",
									new Document("fitbit-user-id", null)
										.append("fitbit-display-name", null)
										.append("fitbit-access-token", null)
										.append("fitbit-refresh-token", null)
										.append("fitbit-scope", null)
										.append("fitbit-token-type", null)
										.append("fitbit-expires-in", -1));
		users.insertOne(newUser);
		
		end();
		
		return newUser;
	}
	
	public static Document loginUser(String username, String password) {
		start();
		
		Document existingUser = users.find(eq("username", username)).first();
		if (existingUser == null || !password.equals(existingUser.getString("password"))) return null;
		
		end();
		
		return existingUser;
	}
}
