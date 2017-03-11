package db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InitDB {
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient(DBTools.HOST, DBTools.PORT);
		MongoDatabase database = mongoClient.getDatabase(DBTools.DATABASE);
		
		MongoCollection<Document> users = database.getCollection(DBTools.USERDB);
		MongoCollection<Document> teams = database.getCollection(DBTools.TEAMDB);
		
		Document dummy = new Document();
		users.insertOne(dummy);
		users.deleteOne(dummy);
		teams.insertOne(dummy);
		teams.deleteOne(dummy);
		
		mongoClient.close();
	}
}
