package db;

import java.util.ArrayList;

import org.bson.Document;

import model.ModelTools;
import model.TeamInvitation;
import model.User;
import util.DateFormat;

public class UserDB implements DB<User> {
	
	@Override
	public Document toDocument(User user) {
		return new Document("username", user.getUsername())
					.append("password", user.getPassword())
					.append("member-since", DateFormat.getFormattedString(user.getMemberSince(), ModelTools.DATE_TIME_FORMAT))
//					.append("info", DBTools.userInfoDB.toDocument(user.getInfo()))
//					.append("settings", DBTools.userSettingsDB.toDocument(user.getSettings()))
//					.append("diabetes-params", DBTools.userDiabetesParamsDB.toDocument(user.getDiabetesParams()))
//					.append("adherence-params", DBTools.userAdherenceParamsDB.toDocument(user.getAdherenceParams()))
//					.append("community-params", DBTools.userCommunityParamsDB.toDocument(user.getCommunityParams()))
//					.append("team", DBTools.teamDB.toDocument(user.getTeam()))
					.append("team-id", user.getTeamId())
					.append("team-invitations", this.teamInvitationsToDocument(user))
					.append("schedule", DBTools.userScheduleDB.toDocument(user.getSchedule()))
					.append("fitbit-account", DBTools.fitBitAccountDB.toDocument(user.getFitBitAccount()));
	}
	
	@Override
	public User fromDocument(Document document) {
		User user = new User(document.getString("username"), document.getString("password"));
		
		user.setMemberSince(DateFormat.getDate(document.getString("member-since"), ModelTools.DATE_TIME_FORMAT));
//		user.setInfo(DBTools.userInfoDB.fromDocument((Document) document.get("info")));
//		user.setSettings(DBTools.userSettingsDB.fromDocument((Document) document.get("settings")));
//		user.setDiabetesParams(DBTools.userDiabetesParamsDB.fromDocument((Document) document.get("diabetes-params")));
//		user.setAdherenceParams(DBTools.userAdherenceParamsDB.fromDocument((Document) document.get("adherence-params")));
//		user.setCommunityParams(DBTools.userCommunityParamsDB.fromDocument((Document) document.get("community-params")));
		user.setTeamId(document.getLong("team-id"));
		user.setTeamInvitations(this.teamInvitationsFromDocument(document));
		user.setSchedule(DBTools.userScheduleDB.fromDocument((Document) document.get("schedule")));
		user.setFitBitAccount(DBTools.fitBitAccountDB.fromDocument((Document) document.get("fitbit-account")));
		
		return user;
	}
	
	/* * */
	
	public Document teamInvitationsToDocument(User user) {
		Document teamInvitationsDocument = new Document();
		for (TeamInvitation teamInvitation : user.getTeamInvitations()) {
			teamInvitationsDocument.append(Long.toString(teamInvitation.getTeamId()),
													DBTools.teamInvitationDB.toDocument(teamInvitation));
		}
		return teamInvitationsDocument;
	}
	
	public ArrayList<TeamInvitation> teamInvitationsFromDocument(Document document) {
		ArrayList<TeamInvitation> teamInvitations = new ArrayList<>();
		Document teamInvitationsDocument = document.get("team-invitations", Document.class);
		for (String teamIdString : teamInvitationsDocument.keySet()) {
			Document teamInvitationDocument =
					teamInvitationsDocument.get(teamIdString, Document.class);
			teamInvitations.add(DBTools.teamInvitationDB.fromDocument(teamInvitationDocument));
		}
		return teamInvitations;
	}
	
}
