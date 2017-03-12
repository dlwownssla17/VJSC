package db;

import org.bson.Document;

import model.Team;

public class TeamDB implements DB<Team> {

	@Override
	public Document toDocument(Team team) {
		return new Document();
	}

	@Override
	public Team fromDocument(Document document) {
		return null;
	}

}
