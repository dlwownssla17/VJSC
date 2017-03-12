package db;

import org.bson.Document;

import model.User;
import model.UserCommunityParams;

public class UserCommunityParamsDB extends ObjectDB {
	public static Document toDocument(UserCommunityParams communityParams) {
		// TODO: implement
		return new Document();
	}
	
	public static UserCommunityParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
