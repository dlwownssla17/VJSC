package db;

import org.bson.Document;

import model.UserDiabetesParams;

public class UserDiabetesParamsDB extends ObjectDB {
	public static Document toDocument(UserDiabetesParams diabetesParams) {
		// TODO: implement
		return new Document();
	}
	
	public static UserDiabetesParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
