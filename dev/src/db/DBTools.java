package db;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Competition;
import model.IDCounter;
import model.Team;
import model.User;

public class DBTools {
	public static String HOST = "localhost";
	public static int PORT = 27017;
	public static String DATABASE_NAME = "debradb";
	
	public static String IDCOUNTERDB = "idcounter";
	public static String USERDB = "user";
	public static String TEAMDB = "team";
	public static String COMPETITIONDB = "competition";
	
	public static MongoClient client;
	public static MongoDatabase database;
	public static MongoCollection<Document> idCounters; // only one
	public static MongoCollection<Document> users;
	public static MongoCollection<Document> teams;
	public static MongoCollection<Document> competitions;
	
	public static CompetitionDB competitionDB = new CompetitionDB();
	public static CompetitionHistoryDB competitionHistoryDB = new CompetitionHistoryDB();
	public static CompetitionInvitationDB competitionInvitationDB = new CompetitionInvitationDB();
	public static FitBitAccountDB fitBitAccountDB = new FitBitAccountDB();
	public static IDCounterDB idCounterDB = new IDCounterDB();
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
		
		idCounters = database.getCollection(IDCOUNTERDB);
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
		if (idCounters.count() == 0) idCounters.insertOne(idCounterDB.toDocument(new IDCounter()));
		users.insertOne(dummy);
		users.deleteOne(dummy);
		teams.insertOne(dummy);
		teams.deleteOne(dummy);
		competitions.insertOne(dummy);
		competitions.deleteOne(dummy);
		
		close();
	}
	
	/* * */
	
	public static Document readIDCounter() {
		open();
		
		Document idCounterDocument = idCounters.find().first();
		
		close();
		
		return idCounterDocument;
	}
	
	public static void writeIDCounter(IDCounter idCounter) {
		open();
		
		idCounters.replaceOne(exists("team-id-counter"), idCounterDB.toDocument(idCounter));
		
		close();
	}
	
	public static Document findUserDocument(String username) {
		open();
		
		Document existingUserDocument = users.find(eq("username", username)).first();
		
		close();
		
		return existingUserDocument;
	}
	
	public static User findUser(Document existingUserDocument) {
		return existingUserDocument == null ? null : userDB.fromDocument(existingUserDocument);
	}
	
	public static User findUser(String username) {
		return findUser(findUserDocument(username));
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
	
	public static void updateUserTeamId(User user) {
		open();
		
		Document newUserTeamId = new Document("team-id", user.getTeamId());
		
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserTeamId));
		
		close();
	}
	
	public static void updateUserTeamInvitations(User user) {
		open();
		
		Document newUserTeamInvitations = new Document("team-invitations", userDB.teamInvitationsToDocument(user));
		
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserTeamInvitations));
		
		close();
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
	
	public static Document findTeamDocument(long teamId) {
		open();
		
		Document existingTeamDocument = teams.find(eq("team-id", teamId)).first();
		
		close();
		
		return existingTeamDocument;
	}
	
	public static Document findTeamDocument(String teamName) {
		open();
		
		Document existingTeamDocument = teams.find(eq("team-name", teamName)).first();
		
		close();
		
		return existingTeamDocument;
	}
	
	public static Team findTeam(Document existingTeamDocument) {
		return existingTeamDocument == null ? null : teamDB.fromDocument(existingTeamDocument);
	}
	
	public static Team findTeam(long teamId) {
		return findTeam(findTeamDocument(teamId));
	}
	
	public static Team findTeam(String teamName) {
		return findTeam(findTeamDocument(teamName));
	}
	
	public static Team createTeam(long teamId, String teamName, String leaderUsername, int maxTeamSize) {
		open();
		
		// team with team id or team name already exists
		if (teams.count(eq("team-id", teamId)) > 0 || teams.count(eq("team-name", teamName)) > 0) return null;
		
		Team newTeam = new Team(teamId, teamName, leaderUsername, maxTeamSize);
		
		teams.insertOne(teamDB.toDocument(newTeam));
		
		close();
		
		return newTeam;
	}
	
	public static void updateTeam(Team team) {
		open();
		
		teams.replaceOne(eq("team-id", team.getTeamId()), teamDB.toDocument(team));
		
		close();
	}
	
	public static void updateTeamLeaderUsername(Team team) {
		open();
		
		Document newTeamLeaderUsername = new Document("leader-username", team.getLeaderUsername());
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamLeaderUsername));
		
		close();
	}
	
	public static void updateTeamMemberUsernames(Team team) {
		open();
		
		Document newTeamMemberUsernames = new Document("member-usernames", teamDB.memberUsernamesToDocument(team));
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamMemberUsernames));
		
		close();
	}
	
	public static void updateTeamUsersInvited(Team team) {
		open();
		
		Document newTeamUsersInvited = new Document("users-invited", teamDB.usersInvitedToDocument(team));
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamUsersInvited));
		
		close();
	}
	
	public static void updateTeamCompetitionId(Team team) {
		open();
		
		Document newTeamCompetitionId = new Document("competition-id", team.getCompetitionId());
		
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionId));
		
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
	
	public static Document findCompetitionDocument(long competitionId) {
		open();
		
		Document existingCompetitionDocument = competitions.find(eq("competition-id", competitionId)).first();
		
		close();
		
		return existingCompetitionDocument;
	}
	
	public static Competition findCompetition(Document existingCompetitionDocument) {
		return existingCompetitionDocument == null ? null : competitionDB.fromDocument(existingCompetitionDocument);
	}
	
	public static Competition findCompetition(long competitionId) {
		return findCompetition(findCompetitionDocument(competitionId));
	}
	
	public static Competition createCompetition(String competitionName, long competitionId,
			Date competitionStartDate, Date competitionEndDate, long teamRedId, long teamBlueId,
			String teamRedName, String teamBlueName, String teamRedLeaderUsername, String teamBlueLeaderUsername,
			boolean showTeamMembers) {
		open();
		
		// competition with competition id already exists
		if (competitions.count(eq("competition-id", competitionId)) > 0) return null;
		
		Competition newCompetition =
				new Competition(competitionName, competitionId, competitionStartDate, competitionEndDate,
						teamRedId, teamBlueId, teamRedName, teamBlueName, teamRedLeaderUsername, teamBlueLeaderUsername,
						showTeamMembers);
		
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
