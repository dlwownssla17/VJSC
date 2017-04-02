package db;

import org.bson.Document;

import model.CompetitionHistory;
import model.CompetitionResult;
import model.CompetitionTeamColor;
import model.ModelTools;
import util.DateFormat;

public class CompetitionHistoryDB implements DB<CompetitionHistory> {

	@Override
	public Document toDocument(CompetitionHistory competitionHistory) {
		return new Document("competition-name", competitionHistory.getCompetitionName())
					.append("competition-id", competitionHistory.getCompetitionId())
					.append("competition-result", competitionHistory.getCompetitionResult().toString())
					.append("team-red-name", competitionHistory.getTeamName(CompetitionTeamColor.RED))
					.append("team-blue-name", competitionHistory.getTeamName(CompetitionTeamColor.BLUE))
					.append("team-red-score", competitionHistory.getTeamScore(CompetitionTeamColor.RED))
					.append("team-blue-score", competitionHistory.getTeamScore(CompetitionTeamColor.BLUE))
					.append("team-red-left", competitionHistory.getTeamLeft(CompetitionTeamColor.RED))
					.append("team-blue-left", competitionHistory.getTeamLeft(CompetitionTeamColor.BLUE))
					.append("competition-start-date",
							DateFormat.getFormattedString(competitionHistory.getCompetitionStartDate(),
																					ModelTools.DATE_FORMAT))
					.append("competition-end-date",
							DateFormat.getFormattedString(competitionHistory.getCompetitionEndDate(),
																					ModelTools.DATE_FORMAT));
	}

	@Override
	public CompetitionHistory fromDocument(Document document) {
		CompetitionResult competitionResult = null;
		String competitionResultString = document.getString("competition-result");
		for (CompetitionResult res : CompetitionResult.values()) {
			if (res.toString().equals(competitionResultString)) {
				competitionResult = res;
				break;
			}
		}
		
		return new CompetitionHistory(document.getString("competition-name"), document.getLong("competition-id"),
				competitionResult, document.getString("team-red-name"), document.getString("team-blue-name"),
				document.getInteger("team-red-score"), document.getInteger("team-blue-score"),
				document.getBoolean("team-red-left"), document.getBoolean("team-blue-left"),
				DateFormat.getDate(document.getString("competition-start-date"), ModelTools.DATE_FORMAT),
				DateFormat.getDate(document.getString("competition-end-date"), ModelTools.DATE_FORMAT));
	}

}
