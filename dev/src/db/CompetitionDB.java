package db;

import java.util.HashMap;

import org.bson.Document;

import model.Competition;
import model.CompetitionTeamColor;
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
					.append("team-red-id", competition.getTeamId(CompetitionTeamColor.RED))
					.append("team-blue-id", competition.getTeamId(CompetitionTeamColor.BLUE))
					.append("team-red-name", competition.getTeamName(CompetitionTeamColor.RED))
					.append("team-blue-name", competition.getTeamName(CompetitionTeamColor.BLUE))
					.append("team-red-leader-username", competition.getTeamLeaderUsername(CompetitionTeamColor.RED))
					.append("team-blue-leader-username", competition.getTeamLeaderUsername(CompetitionTeamColor.BLUE))
					.append("team-red-scores", this.teamScoresToDocument(competition, CompetitionTeamColor.RED))
					.append("team-blue-scores", this.teamScoresToDocument(competition, CompetitionTeamColor.BLUE))
					.append("team-red-left", competition.getTeamLeft(CompetitionTeamColor.RED))
					.append("team-blue-left", competition.getTeamLeft(CompetitionTeamColor.BLUE))
					.append("show-team-members", competition.getShowTeamMembers())
					.append("status", competition.getStatus())
					.append("valid", competition.getValid());
	}

	@Override
	public Competition fromDocument(Document document) {
		Competition competition = new Competition(document.getString("competition-name"),
				document.getLong("competition-id"),
				DateFormat.getDate(document.getString("competition-start-date"), ModelTools.DATE_FORMAT),
				DateFormat.getDate(document.getString("competition-end-date"), ModelTools.DATE_FORMAT),
				document.getLong("team-red-id"), document.getLong("team-blue-id"),
				document.getString("team-red-name"), document.getString("team-blue-name"),
				document.getString("team-red-leader-username"), document.getString("team-blue-leader-username"),
				document.getBoolean("show-team-members"));
		
		competition.setTeamScores(CompetitionTeamColor.RED,
												this.teamScoresFromDocument(document, CompetitionTeamColor.RED));
		competition.setTeamScores(CompetitionTeamColor.BLUE,
												this.teamScoresFromDocument(document, CompetitionTeamColor.BLUE));
		competition.setTeamLeft(CompetitionTeamColor.RED, document.getBoolean("team-red-left"));
		competition.setTeamLeft(CompetitionTeamColor.BLUE, document.getBoolean("team-blue-left"));
		competition.setStatus(document.getBoolean("status"));
		competition.setValid(document.getBoolean("valid"));
		
		return competition;
	}
	
	/* * */
	
	public Document teamScoresToDocument(Competition competition, CompetitionTeamColor color) {
		Document teamScoresDocument = new Document();
		HashMap<String, Integer> teamScores = competition.getTeamScores(color);
		for (String memberUsername : competition.getTeamMembers(color)) {
			teamScoresDocument.append(memberUsername, teamScores.get(memberUsername));
		}
		return teamScoresDocument;
	}
	
	public HashMap<String, Integer> teamScoresFromDocument(Document document, CompetitionTeamColor color) {
		HashMap<String, Integer> teamScores = new HashMap<>();
		Document teamScoresDocument = null;
		switch(color) {
			case RED:
				teamScoresDocument = document.get("team-red-scores", Document.class);
				break;
			case BLUE:
				teamScoresDocument = document.get("team-blue-scores", Document.class);
				break;
			default:
				return null;
		}
		for (String memberUsername : teamScoresDocument.keySet()) {
			teamScores.put(memberUsername, teamScoresDocument.getInteger(memberUsername));
		}
		return teamScores;
	}
	
}
