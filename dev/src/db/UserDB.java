package db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		
	}
}
