package db;

import org.bson.Document;

import model.UserAdherenceParams;

public class UserAdherenceParamsDB extends ObjectDB {
	public static Document toDocument(UserAdherenceParams adherenceParams) {
		// TODO: implement
		return new Document();
	}
	
	public static UserAdherenceParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
