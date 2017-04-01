package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bson.Document;

import model.CompetitionHistory;
import model.CompetitionInvitation;
import model.ModelTools;
import model.Team;
import model.User;
import util.DateFormat;

public class TeamDB implements DB<Team> {

	@Override
	public Document toDocument(Team team) {
		return new Document("team-id", team.getTeamId())
					.append("team-name", team.getTeamName())
					.append("team-created",
							DateFormat.getFormattedString(team.getTeamCreated(), ModelTools.DATE_TIME_FORMAT))
					.append("max-team-size", team.getMaxTeamSize())
					.append("leader-username", team.getLeader().getUsername())
					.append("members", this.membersToDocument(team))
					.append("competition-id", team.hasCompetition() ? team.getCompetition().getCompetitionId() : -1)
					.append("competition-invitations", this.competitionInvitationsToDocument(team))
					.append("competition-histories", this.competitionHistoriesToDocument(team));
	}

	@Override
	public Team fromDocument(Document document) {
		// need to set leader, members, inTeamSince, and competition
		Team team = new Team(document.getLong("team-id"), document.getString("team-name"), null,
																			document.getInteger("max-team-size"));
		
		team.setTeamCreated(DateFormat.getDate(document.getString("team-created"), ModelTools.DATE_TIME_FORMAT));
		team.setCompetitionInvitations(this.competitionInvitationsFromDocument(document));
		team.setCompetitionHistories(this.competitionHistoriesFromDocument(document));
		
		return team;
	}
	
	/* * */
	
	public Document membersToDocument(Team team) {
		Document membersDocument = new Document();
		for (User member : team.getMembers()) {
			membersDocument.append(member.getUsername(),
					new Document("username", member.getUsername())
						.append("in-team-since", DateFormat.getFormattedString(team.getInTeamSinceForMember(member),
																						ModelTools.DATE_TIME_FORMAT)));
		}
		return membersDocument;
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
	
	/* * */
	
	public String getLeaderUsernameFromDocument(Document document) {
		return document.getString("leader-username");
	}
	
	public ArrayList<String> getMemberUsernamesFromDocument(Document document) {
		ArrayList<String> memberUsernames = new ArrayList<>();
		Document membersDocument = document.get("members", Document.class);
		for (String memberUsername : membersDocument.keySet()) {
			memberUsernames.add(memberUsername);
		}
		return memberUsernames;
	}

	public HashMap<String, Date> getMemberUsernamesInTeamSinceFromDocument(Document document) {
		HashMap<String, Date> memberUsernamesInTeamSince = new HashMap<>();
		Document membersDocument = document.get("members", Document.class);
		for (String memberUsername : membersDocument.keySet()) {
			Document memberInTeamSinceDocument = membersDocument.get(memberUsername, Document.class);
			memberUsernamesInTeamSince.put(memberUsername,
					DateFormat.getDate(memberInTeamSinceDocument.getString("in-team-since"),
																					ModelTools.DATE_TIME_FORMAT));
		}
		return memberUsernamesInTeamSince;
	}
	
	public long getCompetitionIdFromDocument(Document document) {
		return document.getLong("competition-id");
	}
}
