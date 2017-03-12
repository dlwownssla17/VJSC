package db;

import org.bson.Document;

import model.UserInfo;

public class UserInfoDB implements DB<UserInfo> {
	
	@Override
	public Document toDocument(UserInfo info) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public UserInfo fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
