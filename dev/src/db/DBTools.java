package db;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Competition;
import model.Team;
import model.User;

public class DBTools {
	public static String HOST = "localhost";
	public static int PORT = 27017;
	public static String DATABASE_NAME = "debradb";
	public static String USERDB = "user";
	public static String TEAMDB = "team";
	public static String COMPETITIONDB = "competition";
	
	public static MongoClient client;
	public static MongoDatabase database;
	public static MongoCollection<Document> users;
	public static MongoCollection<Document> teams;
	public static MongoCollection<Document> competitions;
	
	public static CompetitionDB competitionDB = new CompetitionDB();
	public static CompetitionHistoryDB competitionHistoryDB = new CompetitionHistoryDB();
	public static CompetitionInvitationDB competitionInvitationDB = new CompetitionInvitationDB();
	public static FitBitAccountDB fitBitAccountDB = new FitBitAccountDB();
	public static ProgressDB progressDB = new ProgressDB();
	public static ScheduleItemDB scheduleItemDB = new ScheduleItemDB();
//	public static ScheduleItemNotificationParamsDB scheduleItemNotificationParamsDB = new ScheduleItemNotificationParamsDB();
	public static ScheduleItemRecurrenceDB scheduleItemRecurrenceDB = new ScheduleItemRecurrenceDB();
	public static TeamDB teamDB = new TeamDB();
	public static TeamInvitationDB teamInvitationDB = new TeamInvitationDB();
//	public static UserAdherenceParamsDB userAdherenceParamsDB = new UserAdherenceParamsDB();
//	public static UserCommunityParamsDB userCommunityParamsDB = new UserCommunityParamsDB();
	public static UserDB userDB = new UserDB();
//	public static UserDiabetesParamsDB userDiabetesParamsDB = new UserDiabetesParamsDB();
//	public static UserInfoDB userInfoDB = new UserInfoDB();
	public static UserScheduleDB userScheduleDB = new UserScheduleDB();
//	public static UserSettingsDB userSettingsDB = new UserSettingsDB();
	
	/* * */
	
	public static void open() {
		client = new MongoClient(HOST, PORT);
		database = client.getDatabase(DATABASE_NAME);
		
		users = database.getCollection(USERDB);
		teams = database.getCollection(TEAMDB);
		competitions = database.getCollection(COMPETITIONDB);
	}
	
	public static void close() {
		client.close();
	}
	
	public static void init() {
		open();
		
		Document dummy = new Document();
		users.insertOne(dummy);
		users.deleteOne(dummy);
		teams.insertOne(dummy);
		teams.deleteOne(dummy);
		competitions.insertOne(dummy);
		competitions.deleteOne(dummy);
		
		close();
	}
	
	/* * */
	
	public static User findUser(String username) {
		open();
		
		Document existingUserDocument = users.find(eq("username", username)).first();
		
		close();
		
		return existingUserDocument == null ? null : userDB.fromDocument(existingUserDocument);
	}
	
	public static User createUser(String username, String password) {
		open();
		
		// user with username already exists
		if (users.count(eq("username", username)) > 0) return null;
		
		User newUser = new User(username, password);
		
		users.insertOne(userDB.toDocument(newUser));
		
		close();
		
		return newUser;
	}
	
	public static void updateUser(User user) {
		open();
		
		users.replaceOne(eq("username", user.getUsername()), userDB.toDocument(user));
		
		close();
	}
	
	public static void updateUserTeamInvitations(User user) {
		open();
		
		Document newUserTeamInvitations = new Document("team-invitations", userDB.teamInvitationsToDocument(user));
		
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserTeamInvitations));
	}
	
	public static void updateUserSchedule(User user) {
		open();
		
		Document newUserSchedule = new Document("schedule", userScheduleDB.toDocument(user.getSchedule()));
		
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserSchedule));
		
		close();
	}
	
	public static void updateUserFitBitAccount(User user) {
		open();
		
		Document newUserFitBitAccount =
				new Document("fitbit-account", fitBitAccountDB.toDocument(user.getFitBitAccount()));
		
		users.updateOne(eq("username", user.getUsername()),  new Document("$set", newUserFitBitAccount));
		
		close();
	}
	
	/* * */
	
	public static Team findTeam(long teamId) {
		open();
		
		Document existingTeamDocument = teams.find(eq("team-id", teamId)).first();
		
		close();
		
		return existingTeamDocument == null ? null : teamDB.fromDocument(existingTeamDocument);
	}
	
	public static Team findTeam(String teamName) {
		open();
		
		Document existingTeamDocument = teams.find(eq("team-name", teamName)).first();
		
		close();
		
		return existingTeamDocument == null ? null : teamDB.fromDocument(existingTeamDocument);
	}
	
	public static Team createTeam(long teamId, String teamName, User leader, int maxTeamSize) {
		open();
		
		// team with team id or team name already exists
		if (teams.count(eq("team-id", teamId)) > 0 || teams.count(eq("team-name", teamName)) > 0) return null;
		
		Team newTeam = new Team(teamId, teamName, leader, maxTeamSize);
		
		teams.insertOne(teamDB.toDocument(newTeam));
		
		close();
		
		return newTeam;
	}
	
	public static void updateTeam(Team team) {
		open();
		
		teams.replaceOne(eq("team-id", team.getTeamId()), teamDB.toDocument(team));
		
		close();
	}
	
	public static void updateTeamMembers(Team team) {
		open();
		
		Document newTeamMembers = new Document("members", teamDB.membersToDocument(team));
		
		teams.updateOne(eq("team-id",  team.getTeamId()), new Document("$set", newTeamMembers));
		
		close();
	}
	
	public static void updateTeamCompetitionInvitations(Team team) {
		open();
		
		Document newTeamCompetitionInvitations =
				new Document("competition-invitations", teamDB.competitionInvitationsToDocument(team));
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionInvitations));
		
		close();
	}
	
	public static void updateTeamCompetitionHistories(Team team) {
		open();
		
		Document newTeamCompetitionHistories =
				new Document("competition-histories", teamDB.competitionHistoriesToDocument(team));
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionHistories));
		
		close();
	}
	
	/* * */
	
	public static Competition findCompetition(long competitionId) {
		open();
		
		Document existingCompetitionDocument = competitions.find(eq("competition-id", competitionId)).first();
		
		close();
		
		return existingCompetitionDocument == null ? null : competitionDB.fromDocument(existingCompetitionDocument);
	}
	
	public static Competition createCompetition(String competitionName, long competitionId,
			Date competitionStartDate, Date competitionEndDate, Team teamRed, Team teamBlue, boolean showTeamMembers) {
		open();
		
		// competition with competition id already exists
		if (competitions.count(eq("competition-id", competitionId)) > 0) return null;
		
		Competition newCompetition =
				new Competition(competitionName, competitionId, competitionStartDate, competitionEndDate,
						teamRed, teamBlue, showTeamMembers);
		
		competitions.insertOne(competitionDB.toDocument(newCompetition));
		
		close();
		
		return newCompetition;
	}
	
	public static void updateCompetition(Competition competition) {
		open();
		
		competitions.replaceOne(eq("competition-id", competition.getCompetitionId()),
																			competitionDB.toDocument(competition));
		
		close();
	}
	
	/* * */
	
	
	
	public static void main(String[] args) {
		init();
	}
}
