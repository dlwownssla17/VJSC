package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
					.append("competition-histories", this.competitionHistoriesToDocument(team));
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
	
	public ArrayList<String> memberUsernamesFromDocument(Document document) {
		ArrayList<String> memberUsernames = new ArrayList<>();
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
			inTeamSince.put(memberUsername, DateFormat.getDate(memberUsernamesDocument.getString(memberUsername),
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
	
	public HashSet<String> usersInvitedFromDocument(Document document) {
		HashSet<String> usersInvited = new HashSet<>();
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
