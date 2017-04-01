package db;

import org.bson.Document;

import model.Competition;
import model.ModelTools;
import util.DateFormat;

public class CompetitionDB implements DB<Competition> {

	@Override
	public Document toDocument(Competition competition) {
		return new Document("competition-id", competition.getCompetitionId())
					.append("competition-name", competition.getCompetitionName())
					.append("competition-start-date",
							DateFormat.getFormattedString(competition.getCompetitionStartDate(),
																					ModelTools.DATE_FORMAT))
					.append("competition-end-date",
							DateFormat.getFormattedString(competition.getCompetitionEndDate(),
																					ModelTools.DATE_FORMAT))
					.append("team-red-id", competition.getTeamRed().getTeamId())
					.append("team-blue-id", competition.getTeamBlue().getTeamId())
					.append("team-red-score", competition.getTeamRedScore())
					.append("team-blue-score", competition.getTeamBlueScore())
					.append("team-red-left", competition.getTeamRedLeft())
					.append("team-blue-left", competition.getTeamBlueLeft())
					.append("show-team-members", competition.getShowTeamMembers())
					.append("status", competition.getStatus());
	}

	@Override
	public Competition fromDocument(Document document) {
		// need to set teamRed and teamBlue
		Competition competition = new Competition(document.getString("competition-name"),
				document.getLong("competition-id"),
				DateFormat.getDate(document.getString("competition-start-date"), ModelTools.DATE_FORMAT),
				DateFormat.getDate(document.getString("competition-end-date"), ModelTools.DATE_FORMAT),
				null, null, document.getBoolean("show-team-members"));
		
		competition.setTeamRedScore(document.getInteger("team-red-score"));
		competition.setTeamBlueScore(document.getInteger("team-blue-score"));
		competition.setTeamRedLeft(document.getBoolean("team-red-left"));
		competition.setTeamBlueLeft(document.getBoolean("team-blue-left"));
		competition.setStatus(document.getBoolean("status"));
		
		return competition;
	}
	
	/* * */
	
	public long getTeamRedIdFromDocument(Document document) {
		return document.getLong("team-red-id");
	}
	
	public long getTeamBlueIdFromDocument(Document document) {
		return document.getLong("team-blue-id");
	}
	
}
