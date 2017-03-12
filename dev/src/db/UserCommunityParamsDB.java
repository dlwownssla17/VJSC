package db;

import org.bson.Document;

import model.User;
import model.UserCommunityParams;

public class UserCommunityParamsDB implements DB<UserCommunityParams> {
	
	@Override
	public Document toDocument(UserCommunityParams communityParams) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public UserCommunityParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
