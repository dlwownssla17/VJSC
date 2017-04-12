package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import model.CompetitionHistory;
import model.CompetitionInvitation;
import model.ModelTools;
import model.Team;
import util.DateFormat;

public class TeamDB implements DB<Team> {

	@Override
	public Document toDocument(Team team) {
		return new Document("team-id", team.getTeamId())
					.append("team-name", team.getTeamName())
					.append("team-created",
							DateFormat.getFormattedString(team.getTeamCreated(), ModelTools.DATE_TIME_FORMAT))
					.append("max-team-size", team.getMaxTeamSize())
					.append("leader-username", team.getLeaderUsername())
					.append("member-usernames", this.memberUsernamesToDocument(team))
					.append("users-invited", this.usersInvitedToDocument(team))
					.append("competition-id", team.getCompetitionId())
					.append("competition-invitations", this.competitionInvitationsToDocument(team))
					.append("competition-histories", this.competitionHistoriesToDocument(team))
					.append("valid", team.getValid());
	}

	@Override
	public Team fromDocument(Document document) {
		Team team = new Team(document.getLong("team-id"), document.getString("team-name"),
				document.getString("leader-username"), document.getInteger("max-team-size"));
		
		team.setTeamCreated(DateFormat.getDate(document.getString("team-created"), ModelTools.DATE_TIME_FORMAT));
		team.setMemberUsernames(this.memberUsernamesFromDocument(document));
		team.setInTeamSince(this.inTeamSinceFromDocument(document));
		team.setUsersInvited(this.usersInvitedFromDocument(document));
		team.setCompetitionId(document.getLong("competition-id"));
		team.setCompetitionInvitations(this.competitionInvitationsFromDocument(document));
		team.setCompetitionHistories(this.competitionHistoriesFromDocument(document));
		team.setValid(document.getBoolean("valid"));
		
		return team;
	}
	
	/* * */
	
	public Document memberUsernamesToDocument(Team team) {
		Document memberUsernamesDocument = new Document();
		for (String memberUsername : team.getMemberUsernames()) {
			memberUsernamesDocument.append(memberUsername,
					new Document("username", memberUsername)
						.append("in-team-since",
								DateFormat.getFormattedString(team.getInTeamSinceForMemberUsername(memberUsername),
																						ModelTools.DATE_TIME_FORMAT)));
		}
		return memberUsernamesDocument;
	}
	
	public Set<String> memberUsernamesFromDocument(Document document) {
		Set<String> memberUsernames = new HashSet<>();
		Document memberUsernamesDocument = document.get("member-usernames", Document.class);
		for (String memberUsername : memberUsernamesDocument.keySet()) {
			memberUsernames.add(memberUsername);
		}
		return memberUsernames;
	}
	
	public HashMap<String, Date> inTeamSinceFromDocument(Document document) {
		HashMap<String, Date> inTeamSince = new HashMap<>();
		Document memberUsernamesDocument = document.get("member-usernames", Document.class);
		for (String memberUsername : memberUsernamesDocument.keySet()) {
			Document memberInfoDocument = memberUsernamesDocument.get(memberUsername, Document.class);
			inTeamSince.put(memberUsername, DateFormat.getDate(memberInfoDocument.getString("in-team-since"),
																						ModelTools.DATE_TIME_FORMAT));
		}
		return inTeamSince;
	}
	
	public Document usersInvitedToDocument(Team team) {
		Document usersInvitedDocument = new Document();
		for (String usernameInvited : team.getUsersInvited()) {
			usersInvitedDocument.append(usernameInvited, true);
		}
		return usersInvitedDocument;
	}
	
	public ArrayList<String> usersInvitedFromDocument(Document document) {
		ArrayList<String> usersInvited = new ArrayList<>();
		Document usersInvitedDocument = document.get("users-invited", Document.class);
		for (String usernameInvited : usersInvitedDocument.keySet()) {
			usersInvited.add(usernameInvited);
		}
		return usersInvited;
	}
	
	public Document competitionInvitationsToDocument(Team team) {
		Document competitionInvitationsDocument = new Document();
		for (CompetitionInvitation competitionInvitation : team.getCompetitionInvitations()) {
			competitionInvitationsDocument.append(Long.toString(competitionInvitation.getCompetitionId()),
													DBTools.competitionInvitationDB.toDocument(competitionInvitation));
		}
		return competitionInvitationsDocument;
	}
	
	public ArrayList<CompetitionInvitation> competitionInvitationsFromDocument(Document document) {
		ArrayList<CompetitionInvitation> competitionInvitations = new ArrayList<>();
		Document competitionInvitationsDocument = document.get("competition-invitations", Document.class);
		for (String competitionIdString : competitionInvitationsDocument.keySet()) {
			Document competitionInvitationDocument =
					competitionInvitationsDocument.get(competitionIdString, Document.class);
			competitionInvitations.add(DBTools.competitionInvitationDB.fromDocument(competitionInvitationDocument));
		}
		return competitionInvitations;
	}
	
	public Document competitionHistoriesToDocument(Team team) {
		Document competitionHistoriesDocument = new Document();
		for (CompetitionHistory competitionHistory : team.getCompetitionHistories()) {
			competitionHistoriesDocument.append(Long.toString(competitionHistory.getCompetitionId()),
													DBTools.competitionHistoryDB.toDocument(competitionHistory));
		}
		return competitionHistoriesDocument;
	}
	
	public ArrayList<CompetitionHistory> competitionHistoriesFromDocument(Document document) {
		ArrayList<CompetitionHistory> competitionHistories = new ArrayList<>();
		Document competitionHistoriesDocument = document.get("competition-histories", Document.class);
		for (String competitionIdString : competitionHistoriesDocument.keySet()) {
			Document competitionHistoryDocument =
					competitionHistoriesDocument.get(competitionIdString, Document.class);
			competitionHistories.add(DBTools.competitionHistoryDB.fromDocument(competitionHistoryDocument));
		}
		return competitionHistories;
	}
}
