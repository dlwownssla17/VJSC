package db;

import org.bson.Document;

import model.ModelTools;
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
					.append("team", DBTools.teamDB.toDocument(user.getTeam()))
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
		user.joinTeam(DBTools.teamDB.fromDocument((Document) document.get("team")));
		user.setSchedule(DBTools.userScheduleDB.fromDocument((Document) document.get("schedule")));
		user.setFitBitAccount(DBTools.fitBitAccountDB.fromDocument((Document) document.get("fitbit-account")));
		
		return user;
	}
	
}
