package db;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
	
	public synchronized static void open() {
		client = new MongoClient(HOST, PORT);
		database = client.getDatabase(DATABASE_NAME);
		
		idCounters = database.getCollection(IDCOUNTERDB);
		users = database.getCollection(USERDB);
		teams = database.getCollection(TEAMDB);
		competitions = database.getCollection(COMPETITIONDB);
	}
	
	public synchronized static void close() {
		client.close();
	}
	
	public synchronized static void init() {
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
	
	public synchronized static Document readIDCounter() {
		return idCounters.find().first();
	}
	
	public synchronized static void writeIDCounter(IDCounter idCounter) {
		idCounters.replaceOne(exists("team-id-counter"), idCounterDB.toDocument(idCounter));
	}
	
	/* * */
	
	public synchronized static User findUser(String username) {
		Document existingUserDocument = users.find(eq("username", username)).first();
		return existingUserDocument == null ? null : userDB.fromDocument(existingUserDocument);
	}
	
	public synchronized static User createUser(String username, String password) {
		// user with username already exists
		if (users.count(eq("username", username)) > 0) return null;
		
		User newUser = new User(username, password);
		users.insertOne(userDB.toDocument(newUser));
		return newUser;
	}
	
	public synchronized static void updateUser(User user) {
		users.replaceOne(eq("username", user.getUsername()), userDB.toDocument(user));
	}
	
	public synchronized static void updateUserTeamId(User user) {
		Document newUserTeamId = new Document("team-id", user.getTeamId());
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserTeamId));
	}
	
	public synchronized static void updateUserTeamInvitations(User user) {
		Document newUserTeamInvitations = new Document("team-invitations", userDB.teamInvitationsToDocument(user));
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserTeamInvitations));
	}
	
	public synchronized static void updateUserSchedule(User user) {
		Document newUserSchedule = new Document("schedule", userScheduleDB.toDocument(user.getSchedule()));
		users.updateOne(eq("username", user.getUsername()), new Document("$set", newUserSchedule));
	}
	
	public synchronized static void updateUserFitBitAccount(User user) {
		Document newUserFitBitAccount =
				new Document("fitbit-account", fitBitAccountDB.toDocument(user.getFitBitAccount()));
		users.updateOne(eq("username", user.getUsername()),  new Document("$set", newUserFitBitAccount));
	}
	
	/* * */
	
	public synchronized static Team findTeam(long teamId) {
		Document existingTeamDocument = teams.find(eq("team-id", teamId)).first();
		return existingTeamDocument == null ? null : teamDB.fromDocument(existingTeamDocument);
	}
	
	public synchronized static Team findTeam(String teamName) {
		Document existingTeamDocument = teams.find(eq("team-name", teamName)).first();
		return existingTeamDocument == null ? null : teamDB.fromDocument(existingTeamDocument);
	}
	
	public synchronized static Team createTeam(long teamId, String teamName, String leaderUsername, int maxTeamSize) {
		// team with team id or team name already exists
		if (teams.count(eq("team-id", teamId)) > 0 || teams.count(eq("team-name", teamName)) > 0) return null;
		
		Team newTeam = new Team(teamId, teamName, leaderUsername, maxTeamSize);
		teams.insertOne(teamDB.toDocument(newTeam));
		return newTeam;
	}
	
	public synchronized static void updateTeam(Team team) {
		teams.replaceOne(eq("team-id", team.getTeamId()), teamDB.toDocument(team));
	}
	
	public synchronized static void updateTeamLeaderUsername(Team team) {
		Document newTeamLeaderUsername = new Document("leader-username", team.getLeaderUsername());
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamLeaderUsername));
	}
	
	public synchronized static void updateTeamMemberUsernames(Team team) {
		Document newTeamMemberUsernames = new Document("member-usernames", teamDB.memberUsernamesToDocument(team));
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamMemberUsernames));
	}
	
	public synchronized static void updateTeamUsersInvited(Team team) {
		Document newTeamUsersInvited = new Document("users-invited", teamDB.usersInvitedToDocument(team));
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamUsersInvited));
	}
	
	public synchronized static void updateTeamCompetitionId(Team team) {
		Document newTeamCompetitionId = new Document("competition-id", team.getCompetitionId());
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionId));
	}
	
	public synchronized static void updateTeamCompetitionInvitations(Team team) {
		Document newTeamCompetitionInvitations =
				new Document("competition-invitations", teamDB.competitionInvitationsToDocument(team));
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionInvitations));
	}
	
	public synchronized static void updateTeamCompetitionHistories(Team team) {
		Document newTeamCompetitionHistories =
				new Document("competition-histories", teamDB.competitionHistoriesToDocument(team));
		teams.updateOne(eq("team-id", team.getTeamId()), new Document("$set", newTeamCompetitionHistories));
	}
	
	/* * */
	
	public synchronized static Competition findCompetition(long competitionId) {
		Document existingCompetitionDocument = competitions.find(eq("competition-id", competitionId)).first();
		return existingCompetitionDocument == null ? null : competitionDB.fromDocument(existingCompetitionDocument);
	}
	
	public synchronized static Set<Competition> findAllCompetitions() {
		Set<Competition> competitionsSet = new HashSet<>();
		MongoCursor<Document> itr = competitions.find().iterator();
		while (itr.hasNext()) {
			competitionsSet.add(competitionDB.fromDocument(itr.next()));
		}
		return competitionsSet;
	}
	
	public synchronized static Competition createCompetition(String competitionName, long competitionId,
			Date competitionStartDate, Date competitionEndDate, long teamRedId, long teamBlueId,
			String teamRedName, String teamBlueName, String teamRedLeaderUsername, String teamBlueLeaderUsername,
			boolean showTeamMembers) {
		// competition with competition id already exists
		if (competitions.count(eq("competition-id", competitionId)) > 0) return null;
		
		Competition newCompetition =
				new Competition(competitionName, competitionId, competitionStartDate, competitionEndDate,
						teamRedId, teamBlueId, teamRedName, teamBlueName, teamRedLeaderUsername, teamBlueLeaderUsername,
						showTeamMembers);
		competitions.insertOne(competitionDB.toDocument(newCompetition));
		return newCompetition;
	}
	
	public synchronized static void updateCompetition(Competition competition) {
		competitions.replaceOne(eq("competition-id", competition.getCompetitionId()),
																			competitionDB.toDocument(competition));
	}
}
