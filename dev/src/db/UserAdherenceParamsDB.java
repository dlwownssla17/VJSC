package db;

import org.bson.Document;

import model.UserAdherenceParams;

public class UserAdherenceParamsDB implements DB<UserAdherenceParams> {
	
	@Override
	public Document toDocument(UserAdherenceParams adherenceParams) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public UserAdherenceParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
