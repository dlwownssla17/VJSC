package db;

import org.bson.Document;

import model.UserInfo;

public class UserInfoDB extends ObjectDB {
	public static Document toDocument(UserInfo info) {
		// TODO: implement
		return new Document();
	}
	
	public static UserInfo fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
