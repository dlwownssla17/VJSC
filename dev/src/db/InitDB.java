package db;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InitDB extends ObjectDB {
	public static void main(String[] args) {
		start();
		
		Document dummy = new Document();
		users.insertOne(dummy);
		users.deleteOne(dummy);
		teams.insertOne(dummy);
		teams.deleteOne(dummy);
		
		end();
	}
}
