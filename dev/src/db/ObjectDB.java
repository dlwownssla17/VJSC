package db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ObjectDB {
	protected static MongoClient mongoClient;
	protected static MongoDatabase database;
	protected static MongoCollection<Document> users;
	protected static MongoCollection<Document> teams;
	
	protected static void start() {
		mongoClient = new MongoClient(DBTools.HOST, DBTools.PORT);
		database = mongoClient.getDatabase(DBTools.DATABASE);
		
		users = database.getCollection(DBTools.USERDB);
		teams = database.getCollection(DBTools.TEAMDB);
	}
	
	protected static void end() {
		mongoClient.close();
	}
}
