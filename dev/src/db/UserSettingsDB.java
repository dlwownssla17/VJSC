package db;

import org.bson.Document;

import model.UserSettings;

public class UserSettingsDB implements DB<UserSettings> {
	
	@Override
	public Document toDocument(UserSettings settings) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public UserSettings fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
