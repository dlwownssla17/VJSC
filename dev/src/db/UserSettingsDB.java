package db;

import org.bson.Document;

import model.UserSettings;

public class UserSettingsDB extends ObjectDB {
	public static Document toDocument(UserSettings settings) {
		// TODO: implement
		return new Document();
	}
	
	public static UserSettings fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
