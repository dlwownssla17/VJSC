package db;

import org.bson.Document;

import model.CompetitionInvitation;
import model.CompetitionTeamColor;
import model.ModelTools;
import util.DateFormat;

public class CompetitionInvitationDB implements DB<CompetitionInvitation> {

	@Override
	public Document toDocument(CompetitionInvitation competitionInvitation) {
		return new Document("competition-name", competitionInvitation.getCompetitionName())
					.append("competition-id", competitionInvitation.getCompetitionId())
					.append("competition-start-date",
							DateFormat.getFormattedString(competitionInvitation.getCompetitionStartDate(),
																						ModelTools.DATE_FORMAT))
					.append("competition-end-date",
							DateFormat.getFormattedString(competitionInvitation.getCompetitionEndDate(),
																						ModelTools.DATE_FORMAT))
					.append("other-team-name", competitionInvitation.getOtherTeamName())
					.append("other-team-leader-username", competitionInvitation.getOtherTeamLeaderUsername())
					.append("other-team-color", competitionInvitation.getOtherTeamColor().toString());
	}

	@Override
	public CompetitionInvitation fromDocument(Document document) {
		return new CompetitionInvitation(document.getString("competition-name"), document.getLong("competition-id"),
				DateFormat.getDate(document.getString("competition-start-date"), ModelTools.DATE_FORMAT),
				DateFormat.getDate(document.getString("competition-end-date"), ModelTools.DATE_FORMAT),
				document.getString("other-team-name"), document.getString("other-team-leader-username"),
				this.otherTeamColorFromDocument(document));
	}
	
	/* * */
	
	public CompetitionTeamColor otherTeamColorFromDocument(Document document) {
		String otherTeamColorString = document.getString("other-team-color");
		for (CompetitionTeamColor otherTeamColor : CompetitionTeamColor.values()) {
			if (otherTeamColor.toString().equals(otherTeamColorString)) return otherTeamColor;
		}
		return null;
	}

}
