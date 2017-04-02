package db;

import org.bson.Document;

import model.IDCounter;

public class IDCounterDB implements DB<IDCounter> {

	@Override
	public Document toDocument(IDCounter idCounter) {
		return new Document("team-id-counter", idCounter.getTeamIdCounter())
					.append("competition-id-counter", idCounter.getCompetitionIdCounter());
	}

	@Override
	public IDCounter fromDocument(Document document) {
		IDCounter idCounter = new IDCounter();
		idCounter.setTeamIdCounter(document.getLong("team-id-counter"));
		idCounter.setCompetitionIdCounter(document.getLong("competition-id-counter"));
		return idCounter;
	}

}
