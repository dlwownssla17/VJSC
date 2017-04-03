package route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import db.DBTools;
import model.BooleanProgress;
import model.Competition;
import model.CompetitionHistory;
import model.CompetitionInvitation;
import model.CompetitionTeamColor;
import model.IDCounter;
import model.ModelTools;
import model.PercentageProgress;
import model.Progress;
import model.ProgressColor;
import model.ScheduleItem;
import model.ScheduleItemRecurrence;
import model.Team;
import model.TeamInvitation;
import model.User;
import util.CreateLookupDate;
import util.DateAndCalendar;
import util.DateFormat;
import util.RGB;

public class Server {	
	public static int JSON_INDENT = 4;
	
	private static IDCounter ID_COUNTER;
	
	public static void main(String[] args) throws Exception {
		ID_COUNTER = DBTools.idCounterDB.fromDocument(DBTools.readIDCounter());
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        /* Homepage */
        
        server.createContext("/",  new HomeHandler());
        
        /* Login/Logout */
        
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        
        /* Schedule */
        
        server.createContext("/day/view", new DayViewHandler());
        server.createContext("/day/add", new DayAddHandler());
        server.createContext("/day/edit", new DayEditHandler());
        server.createContext("/day/remove", new DayRemoveHandler());
        server.createContext("/month/view", new MonthViewHandler());
        server.createContext("/checkin/view", new CheckInViewHandler());
        server.createContext("/checkin/submit", new CheckInSubmitHandler());
        server.createContext("/update-daily-scores", new UpdateDailyScoresHandler());
        
        /* Community */
        
        server.createContext("/team/view", new TeamViewHandler());
        server.createContext("/team/leader/create", new TeamCreateHandler());
        server.createContext("/team/leader/remove", new TeamRemoveHandler());
        server.createContext("/team/leader/invite", new TeamInviteHandler());
        server.createContext("/team/member/join", new TeamJoinHandler());
        server.createContext("/team/member/decline", new TeamDeclineHandler());
        server.createContext("/team/leader/dismiss", new TeamDismissHandler());
        server.createContext("/competition/view", new CompetitionViewHandler());
        server.createContext("/competition/leader/create", new CompetitionCreateHandler());
        server.createContext("/competition/leader/cancel", new CompetitionCancelHandler());
        server.createContext("/competition/leader/join", new CompetitionJoinHandler());
        server.createContext("/competition/leader/decline", new CompetitionDeclineHandler());
        server.createContext("/competition/leader/leave", new CompetitionLeaveHandler());
        
        // "remove" team
        
        // "remove" competition
        
        server.setExecutor(null); // creates a default executor
        server.start();
	}
	
	/* * */
	
	public static JSONObject toJSON(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is,"utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		
		int c;
		while ((c = br.read()) != -1) {
			sb.append((char) c);
		}
		
		br.close();
		isr.close();
		
		return new JSONObject(sb.toString());
	}
	
	public static JSONArray buildDailyItemsJSON(User user, Date date, boolean checkForActive) {
		ArrayList<ScheduleItem> dailyItems = user.getSchedule().getItemsForDate(date);
		
		JSONArray dailyItemsJSON = new JSONArray();
		for (ScheduleItem item : dailyItems) {
			if (checkForActive && !item.isActive()) continue;
			
			JSONObject itemJSON = new JSONObject();
			
			itemJSON.put("Schedule-Item-ID", item.getId());
			
			ScheduleItemRecurrence recurrence = item.getRecurrence();
			if (recurrence == null) {
				itemJSON.put("Recurring-ID", -1);
				itemJSON.put("Recurring-Type", -1);
				itemJSON.put("Recurring-Value", new JSONArray());
				itemJSON.put("Ending-Type", -1);
				itemJSON.put("Ending-Value", -1);
			} else {
				itemJSON.put("Recurring-ID", recurrence.getRecurringId());
				itemJSON.put("Recurring-Type", recurrence.getRecurringType());
				JSONArray recurringValueJSON = new JSONArray();
				for (int num : recurrence.getRecurringValue()) {
					recurringValueJSON.put(num);
				}
				itemJSON.put("Recurring-Value", recurringValueJSON);
				int endType = recurrence.getEndType();
				itemJSON.put("Ending-Type", endType);
				if (endType == 0) itemJSON.put("Ending-Value", -1);
				else if (endType == 1) itemJSON.put("Ending-Value", recurrence.getEndAfter());
				else if (endType == 2) itemJSON.put("Ending-Value",
												DateFormat.getFormattedString(recurrence.getEndDateTime(),
												ModelTools.DATE_FORMAT));
				else throw new JSONException("invalid recurrence end type.");
			}
			
			itemJSON.put("Schedule-Item-Title", item.getTitle());
			itemJSON.put("Schedule-Item-Description", item.getDescription());
			itemJSON.put("Schedule-Item-Type", item.getType().toString());
			itemJSON.put("Schedule-Item-Start",
					DateFormat.getFormattedString(item.getStartDateTime(), ModelTools.TIME_FORMAT));
			
			String progressType = "";
			Progress progress = item.getProgress();
			if (progress instanceof BooleanProgress) progressType = "boolean";
			else if (progress instanceof PercentageProgress) progressType = "percentage";
			else ; // not implemented levels progress type yet
			itemJSON.put("Schedule-Item-Progress-Type", progressType);
			itemJSON.put("Schedule-Item-Duration", item.getDuration());
			itemJSON.put("Schedule-Item-Progress", progress.getProgress());
			
			itemJSON.put("Schedule-Item-Score", item.getScore());
			itemJSON.put("Schedule-Item-Active", item.isActive());
			
			JSONArray colorRGBArray = new JSONArray();
			for (int num : RGB.progressColorToRGB(item.getProgressColor())) {
				colorRGBArray.put(num);
			}
			itemJSON.put("Schedule-Item-Color", colorRGBArray);
			
			itemJSON.put("Schedule-Item-Modifiable", item.isModifiable());
			itemJSON.put("Schedule-Item-Checked-In-At-Start", item.isCheckedInAtStart());
			
			dailyItemsJSON.put(itemJSON);
		}
		
		return dailyItemsJSON;
	}
	
//	public static void inTeamCheck(User user) {
//		if (!user.inTeam()) throw new IllegalStateException("User is not in team.");
//	}
	
	public static void leaderCheck(String username, Team team) {
		if (!team.isLeader(username)) throw new IllegalStateException("User is not a leader.");
	}
	
	public static void hasCompetitionCheck(Team team) {
		if (!team.hasCompetition()) throw new IllegalStateException("Team does not have competition.");
	}
	
	public static void endCompetition(Team team1, Team team2, Competition competition, Team teamLeaving) {
		if (teamLeaving != null && !(team1 == teamLeaving || team2 == teamLeaving))
			throw new IllegalArgumentException("teamLeaving must be null or referentially same as one of two teams.");
		
		if (teamLeaving != null) {
			CompetitionTeamColor color = teamLeaving.getTeamId() == competition.getTeamId(CompetitionTeamColor.RED) ?
					CompetitionTeamColor.RED : CompetitionTeamColor.BLUE;
			competition.setTeamLeft(color, true);
		}
		
		team1.addCompetitionHistory(competition.toCompetitionHistory(team1.getTeamId()));
		team2.addCompetitionHistory(competition.toCompetitionHistory(team2.getTeamId()));
		
		team1.setCompetitionId(-1);
		team2.setCompetitionId(-1);
		
		competition.setValid(false);
	}
	
	/*
	static class TemplateHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String foo = headers.getFirst("Foo");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
		}
		
	}
	*/
	
	/* * */
	
	static class HomeHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling home page...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				User user = DBTools.findUser(username);
				
				JSONObject responseJSON = new JSONObject();
				
				responseJSON.put("User-Score", user.getTotalRunningScore());
				responseJSON.put("Today-Score-So-Far", user.getTodayScoreSoFar());
				// TODO: change value for Today-Score-So-Far if we incorporate more advanced scoring algorithm
				
				String response = responseJSON.toString(JSON_INDENT);
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else if (requestMethod.equals("POST")) {
				
			} else {
				
			}
			
			System.out.println("handled home page");
		}
	}
	
	/* * */
	
	static class RegisterHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling register...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				String password = headers.getFirst("Password");
				
				User newUser = DBTools.createUser(username, password);
				
				String response = "";
				int rc = newUser != null ? 200 : 401;
				t.sendResponseHeaders(rc, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled register");
		}
		
	}
	
	static class LoginHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling login...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				String password = headers.getFirst("Password");
				
				User existingUser = DBTools.findUser(username);
				
				if (existingUser != null && !existingUser.getPassword().equals(password)) existingUser = null;
				
				String response = "";
				int rc = existingUser != null ? 200 : 401;
				t.sendResponseHeaders(rc, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled login");
		}
		
	}
	
	static class LogoutHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling logout...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				
				// nothing for now
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled logout");
		}
		
	}
	
	/* * */
	
	static class DayViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling day view...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(headers.getFirst("Date"));
				
				JSONObject responseJSON = new JSONObject();
				
				User user = DBTools.findUser(username);
				
				JSONArray dailyItemsJSON = user == null ? new JSONArray() : buildDailyItemsJSON(user, date, false);
				responseJSON.put("Daily-Items", dailyItemsJSON);
				
				int dailyScore = user == null ? -1 : user.getSchedule().computeDailyScore(date);
				responseJSON.put("Daily-Score", dailyScore);
				
				String response = responseJSON.toString(JSON_INDENT);
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else if (requestMethod.equals("POST")) {
				
			} else {
				
			}

			System.out.println("handled day view");
		}
		
	}
	
	static class DayAddHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling day add...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(headers.getFirst("Date"));
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				int recurringType = requestJSON.getInt("Recurring-Type");
				JSONArray recurringValueJSON = requestJSON.getJSONArray("Recurring-Value");
				int[] recurringValue = new int[recurringValueJSON.length()];
				for (int i = 0; i < recurringValueJSON.length(); i++) {
					recurringValue[i] = recurringValueJSON.getInt(i);
				}
				int endType = requestJSON.getInt("Ending-Type");
				String endValue = requestJSON.getString("Ending-Value");
				
				String title = requestJSON.getString("Schedule-Item-Title");
				String description = requestJSON.getString("Schedule-Item-Description");
				String typeString = requestJSON.getString("Schedule-Item-Type");
				String startTimeString = requestJSON.getString("Schedule-Item-Start");
				String progressTypeString = requestJSON.getString("Schedule-Item-Progress-Type");
				int duration = requestJSON.getInt("Schedule-Item-Duration");
				
				User user = DBTools.findUser(username);
				user.getSchedule().addScheduleItem(date, recurringType, recurringValue, endType, endValue,
						title, description, typeString, startTimeString, progressTypeString, duration);
				
				DBTools.updateUserSchedule(user);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled day add");
		}
		
	}
	
	static class DayEditHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling day edit...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(headers.getFirst("Date"));
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long id = requestJSON.getLong("Schedule-Item-ID");
				long recurringId = requestJSON.getLong("Recurring-ID");
				String title = requestJSON.getString("Schedule-Item-Title");
				String description = requestJSON.getString("Schedule-Item-Description");
				String startTimeString = requestJSON.getString("Schedule-Item-Start");
				int duration = requestJSON.getInt("Schedule-Item-Duration");
				
				User user = DBTools.findUser(username);
				user.getSchedule().editScheduleItem(date, id, recurringId, title, description, startTimeString, duration);
				
				DBTools.updateUserSchedule(user);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled day edit");
		}
		
	}
	
	static class DayRemoveHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling day remove...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(headers.getFirst("Date"));
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long id = requestJSON.getLong("Schedule-Item-ID");
				long recurringId = requestJSON.getLong("Recurring-ID");
				
				User user = DBTools.findUser(username);
				user.getSchedule().removeScheduleItem(date, id, recurringId);
				
				DBTools.updateUserSchedule(user);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled day remove");
		}
		
	}
	
	static class MonthViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling month view...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				int year = Integer.parseInt(headers.getFirst("Year"));
				int month = Integer.parseInt(headers.getFirst("Month"));
				
				JSONObject responseJSON = new JSONObject();
				
				User user = DBTools.findUser(username);
				
				ArrayList<Date> datesOfMonth = user.getSchedule().getDatesOfMonthInDailyScores(username, year, month);
				ArrayList<Integer> scores = new ArrayList<>();
				ArrayList<ProgressColor> colors = new ArrayList<>();
				for (Date date : datesOfMonth) {
					scores.add(user.getSchedule().getScoreForDate(date));
					colors.add(user.getSchedule().getProgressColor(date));
				}
				
				JSONArray dayInfoList = new JSONArray();
				for (int i = 0; i < datesOfMonth.size(); i++) {
					JSONObject dayInfo = new JSONObject();
					dayInfo.put("Date", DateFormat.getFormattedString(datesOfMonth.get(i), ModelTools.DATE_FORMAT));
					dayInfo.put("Daily-Score", scores.get(i));
					JSONArray colorRGBArray = new JSONArray();
					for (int num : RGB.progressColorToRGB(colors.get(i))) {
						colorRGBArray.put(num);
					}
					dayInfo.put("Daily-Color", colorRGBArray);
					
					dayInfoList.put(dayInfo);
				}
				responseJSON.put("Day-Info", dayInfoList);
				
				String response = responseJSON.toString(JSON_INDENT);
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else if (requestMethod.equals("POST")) {
				
			} else {
				
			}

			System.out.println("handled month view");
		}
		
	}
	
	static class CheckInViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling check-in view...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(DateAndCalendar.newDateGMT());
				
				JSONObject responseJSON = new JSONObject();
				
				User user = DBTools.findUser(username);
				
				JSONArray dailyItemsCheckInJSON = user == null ? new JSONArray() : buildDailyItemsJSON(user, date, true);
				
				responseJSON.put("Daily-Items", dailyItemsCheckInJSON);
				
				String response = responseJSON.toString(JSON_INDENT);
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else if (requestMethod.equals("POST")) {
				
			} else {
				
			}

			System.out.println("handled check-in view");
		}
		
	}
	
	static class CheckInSubmitHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling check-in submit...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(DateAndCalendar.newDateGMT());
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long id = requestJSON.getLong("Schedule-Item-ID");
				double progress = requestJSON.getDouble("Progress");
				
				User user = DBTools.findUser(username);
				
				user.getSchedule().getItemForDate(date, id).checkIn(progress);
				
				// TODO: incorporate community stuff
				
				DBTools.updateUserSchedule(user);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled check-in submit");
		}
		
	}
	
	static class UpdateDailyScoresHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling update daily scores...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				User user = DBTools.findUser(username);
				
				Date lastDayCheckedDate = CreateLookupDate.getInstance(requestJSON.getString("Last-Day-Checked"));
				
				if (lastDayCheckedDate.before(user.getMemberSince()))
					lastDayCheckedDate = CreateLookupDate.getInstance((Date) user.getMemberSince().clone());
				user.getSchedule().updateDailyScores(lastDayCheckedDate);
				
				DBTools.updateUser(user);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}

			System.out.println("handled update daily scores");
		}
		
	}
	
	/* * */
	
	static class TeamViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling community home...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject responseJSON = new JSONObject();
				
				User user = DBTools.findUser(username);
				Team team = user.inTeam() ? DBTools.findTeam(user.getTeamId()) : null;
				Competition competition = team.hasCompetition() ?
															DBTools.findCompetition(team.getCompetitionId()) : null;
				
				responseJSON.put("In-Team", user.inTeam());
				
				JSONArray teamInvitationsJSON = new JSONArray();
				for (TeamInvitation teamInvitation : user.getTeamInvitations()) {
					JSONObject teamInvitationJSON = new JSONObject();
					teamInvitationJSON.put("Team-Name", teamInvitation.getTeamName());
					teamInvitationJSON.put("Team-ID", teamInvitation.getTeamId());
					teamInvitationJSON.put("Team-Leader", teamInvitation.getTeamLeaderUsername());
					teamInvitationJSON.put("Team-Created",
							DateFormat.getFormattedString(teamInvitation.getTeamCreated(),
																						ModelTools.DATE_TIME_FORMAT));
					
					teamInvitationsJSON.put(teamInvitationJSON);
				}
				responseJSON.put("Invitations", teamInvitationsJSON);
				
				JSONObject teamJSON = new JSONObject();
				if (team != null) {
					teamJSON.put("Is-Leader", team.isLeader(username));
					teamJSON.put("Team-Name", team.getTeamName());
					teamJSON.put("Team-ID", team.getTeamId());
					teamJSON.put("Max-Team-Size", team.getMaxTeamSize());
					teamJSON.put("Team-Size", team.getTeamSize());
					
					JSONArray teamMembersJSON = new JSONArray();
					for (String memberUsername : team.getMemberUsernames()) {
						JSONObject teamMemberJSON = new JSONObject();
						
						User teamMember = DBTools.findUser(memberUsername);
						
						teamMemberJSON.put("Username", teamMember.getUsername());
						teamMemberJSON.put("User-Score", teamMember.getTotalRunningScore());
						teamMemberJSON.put("Today-Score-So-Far", teamMember.getTodayScoreSoFar());
						teamMemberJSON.put("In-Team-Since",
								DateFormat.getFormattedString(team.getInTeamSinceForMemberUsername(memberUsername),
																						ModelTools.DATE_TIME_FORMAT));
						teamMemberJSON.put("Is-Leader", team.isLeader(memberUsername));
						
						teamMembersJSON.put(teamMemberJSON);
					}
					teamJSON.put("Team-Members", teamMembersJSON);
					
					JSONArray usersInvitedJSON = new JSONArray();
					for (String usernameInvited : team.getUsersInvited()) {
						JSONObject userInvitedJSON = new JSONObject();
						userInvitedJSON.put("Username", usernameInvited);
						
						usersInvitedJSON.put(userInvitedJSON);
					}
					teamJSON.put("Users-Invited", usersInvitedJSON);
					
					JSONArray teamHistoriesJSON = new JSONArray();
					for (CompetitionHistory competitionHistory : team.getCompetitionHistories()) {
						JSONObject teamHistoryJSON = new JSONObject();
						teamHistoryJSON.put("Competition-Name", competitionHistory.getCompetitionName());
						teamHistoryJSON.put("Competition-Result", competitionHistory.getCompetitionResult().toString());
						
						JSONObject statsJSON = new JSONObject();
						statsJSON.put("Team-Red-Name", competitionHistory.getTeamName(CompetitionTeamColor.RED));
						statsJSON.put("Team-Red-Score", competitionHistory.getTeamScore(CompetitionTeamColor.RED));
						statsJSON.put("Team-Red-Left", competitionHistory.getTeamLeft(CompetitionTeamColor.RED));
						statsJSON.put("Team-Blue-Name", competitionHistory.getTeamName(CompetitionTeamColor.BLUE));
						statsJSON.put("Team-Blue-Score", competitionHistory.getTeamScore(CompetitionTeamColor.BLUE));
						statsJSON.put("Team-Blue-Left", competitionHistory.getTeamLeft(CompetitionTeamColor.BLUE));
						teamHistoryJSON.put("Stats", statsJSON);
						
						teamHistoryJSON.put("Competition-Start-Date",
								DateFormat.getFormattedString(competitionHistory.getCompetitionStartDate(),
																						ModelTools.DATE_FORMAT));
						teamHistoryJSON.put("Competition-End-Date",
								DateFormat.getFormattedString(competitionHistory.getCompetitionEndDate(),
																						ModelTools.DATE_FORMAT));
						
						teamHistoriesJSON.put(teamHistoryJSON);
					}
					teamJSON.put("Team-History", teamHistoriesJSON);
					
					teamJSON.put("Competition-Status",
							competition != null ? (competition.getStatus() ? "active" : "pending") : "none");
					teamJSON.put("Competition-ID", competition != null ? competition.getCompetitionId() : -1);
					
					JSONArray competitionInvitationsJSON = new JSONArray();
					for (CompetitionInvitation competitionInvitation : team.getCompetitionInvitations()) {
						JSONObject competitionInvitationJSON = new JSONObject();
						competitionInvitationJSON.put("Competition-Name", competitionInvitation.getCompetitionName());
						competitionInvitationJSON.put("Competition-ID", competitionInvitation.getCompetitionId());
						competitionInvitationJSON.put("Competition-Start-Date",
								DateFormat.getFormattedString(competitionInvitation.getCompetitionStartDate(),
																							ModelTools.DATE_FORMAT));
						competitionInvitationJSON.put("Competition-End-Date",
								DateFormat.getFormattedString(competitionInvitation.getCompetitionEndDate(),
																							ModelTools.DATE_FORMAT));
						competitionInvitationJSON.put("Other-Team-Name", competitionInvitation.getOtherTeamName());
						competitionInvitationJSON.put("Other-Team-Leader",
																competitionInvitation.getOtherTeamLeaderUsername());
						competitionInvitationJSON.put("Other-Team-Color",
																competitionInvitation.getOtherTeamColor().toString());
						
						competitionInvitationsJSON.put(competitionInvitationJSON);
					}
					teamJSON.put("Competition-Invitations", competitionInvitationsJSON);
				}
				responseJSON.put("Team", teamJSON);
				
				String response = responseJSON.toString(JSON_INDENT);
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else if (requestMethod.equals("POST")) {
				
			} else {
				
			}
			
			System.out.println("handled community home...");
		}
		
	}
	
	static class TeamCreateHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling create team...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				String teamName = requestJSON.getString("Team-Name");
				int maxTeamSize = requestJSON.getInt("Max-Team-Size");
				
				User user = DBTools.findUser(username);
				Team team = DBTools.createTeam(ID_COUNTER.nextTeamIdCounter(), teamName, username, maxTeamSize);
				
				user.setTeamId(team.getTeamId());
				user.clearTeamInvitations();
				
				DBTools.updateUserTeamInvitations(user);
				DBTools.updateUserTeamId(user);
				DBTools.writeIDCounter(ID_COUNTER);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled create team...");
		}
		
	}
	
	static class TeamRemoveHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling remove team...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long teamId = requestJSON.getLong("Team-ID");
				
				Team team = DBTools.findTeam(teamId);
				leaderCheck(username, team);
				
				// leave competition if team has competition
				if (team.hasCompetition()) {
					Competition competition = DBTools.findCompetition(team.getCompetitionId());
					Team otherTeam = DBTools.findTeam(competition.otherTeamId(teamId));
					endCompetition(team, otherTeam, competition, team);
					
					DBTools.updateTeamCompetitionHistories(otherTeam);
					DBTools.updateTeamCompetitionId(otherTeam);
					DBTools.updateCompetition(competition);
				}
				
				// undo all team invitations to users
				for (String usernameInvited : team.getUsersInvited()) {
					User userInvited = DBTools.findUser(usernameInvited);
					
					team.removeUserInvited(usernameInvited);
					userInvited.removeTeamInvitation(teamId);
					
					DBTools.updateUserTeamInvitations(userInvited);
				}
				
				// remove reference to this team for all current members
				for (String memberUsername : team.getMemberUsernames()) {
					User member = DBTools.findUser(memberUsername);
					
					member.setTeamId(-1);
					
					DBTools.updateUserTeamId(member);
				}
				
				// set valid to false
				team.setValid(false);
				
				DBTools.updateTeam(team);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled remove team...");
		}
		
	}
	
	static class TeamInviteHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling invite member...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled invite member...");
		}
		
	}
	
	static class TeamJoinHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling join team...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handling join team...");
		}
		
	}
	
	static class TeamDeclineHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling decline team invite...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long teamIdInviting = requestJSON.getLong("Team-ID");
				
				User userInvited = DBTools.findUser(username);
				Team teamInviting = DBTools.findTeam(teamIdInviting);
				
				userInvited.removeTeamInvitation(teamIdInviting);
				teamInviting.removeUserInvited(username);
				
				DBTools.updateTeamUsersInvited(teamInviting);
				DBTools.updateUserTeamInvitations(userInvited);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled decline team invite...");
		}
		
	}
	
	static class TeamDismissHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling kick out member...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled kick out member...");
		}
		
	}
	
	static class CompetitionCreateHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling create competition...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled create competition...");
		}
		
	}
	
	static class CompetitionCancelHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling cancel competition...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled cancel competition...");
		}
		
	}
	
	static class CompetitionViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling view competition...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled view competition...");
		}
		
	}
	
	static class CompetitionJoinHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling join competition...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled join competition...");
		}
		
	}
	
	static class CompetitionDeclineHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling decline competition invite...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				// to do
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled decline competition invite...");
		}
		
	}
	
	static class CompetitionLeaveHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling leave competition...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				
				JSONObject requestJSON = toJSON(t.getRequestBody());
				
				long teamId = requestJSON.getLong("Team-ID");
				
				Team team = DBTools.findTeam(teamId);
				leaderCheck(username, team);
				hasCompetitionCheck(team);
				Competition competition = DBTools.findCompetition(team.getCompetitionId());
				Team otherTeam = DBTools.findTeam(competition.otherTeamId(teamId));
				
				endCompetition(team, otherTeam, competition, team);
				
				DBTools.updateTeamCompetitionHistories(team);
				DBTools.updateTeamCompetitionId(team);
				DBTools.updateTeamCompetitionHistories(otherTeam);
				DBTools.updateTeamCompetitionId(otherTeam);
				DBTools.updateCompetition(competition);
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
			
			System.out.println("handled leave competition...");
		}
		
	}
	
}
