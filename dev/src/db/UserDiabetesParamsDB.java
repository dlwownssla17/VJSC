package db;

import org.bson.Document;

import model.UserDiabetesParams;

public class UserDiabetesParamsDB implements DB<UserDiabetesParams> {
	
	@Override
	public Document toDocument(UserDiabetesParams diabetesParams) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public UserDiabetesParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
