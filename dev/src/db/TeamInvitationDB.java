package db;

import org.bson.Document;

import model.ModelTools;
import model.TeamInvitation;
import util.DateFormat;

public class TeamInvitationDB implements DB<TeamInvitation> {

	@Override
	public Document toDocument(TeamInvitation teamInvitation) {
		return new Document("team-name", teamInvitation.getTeamName())
					.append("team-id", teamInvitation.getTeamId())
					.append("team-leader-username", teamInvitation.getTeamLeaderUsername())
					.append("team-created", DateFormat.getFormattedString(teamInvitation.getTeamCreated(),
																					ModelTools.DATE_TIME_FORMAT));
	}

	@Override
	public TeamInvitation fromDocument(Document document) {
		return new TeamInvitation(document.getString("team-name"), document.getLong("team-id"),
				document.getString("team-leader-username"), DateFormat.getDate(document.getString("team-created"),
																					ModelTools.DATE_TIME_FORMAT));
	}

}
