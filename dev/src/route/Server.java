package route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
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
import model.ModelTools;
import model.PercentageProgress;
import model.Progress;
import model.ProgressColor;
import model.ScheduleItem;
import model.ScheduleItemRecurrence;
import model.User;
import util.CreateLookupDate;
import util.DateAndCalendar;
import util.DateFormat;
import util.RGB;

public class Server {
	public static int JSON_INDENT = 4;
	
	public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/",  new HomeHandler());
        
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        
        server.createContext("/day/view", new DayViewHandler());
        server.createContext("/day/add", new DayAddHandler());
        server.createContext("/day/edit", new DayEditHandler());
        server.createContext("/day/remove", new DayRemoveHandler());
        
        server.createContext("/month/view", new MonthViewHandler());
        
        server.createContext("/checkin/view", new CheckInViewHandler());
        server.createContext("/checkin/submit", new CheckInSubmitHandler());
        
        server.createContext("/update-daily-scores", new UpdateDailyScoresHandler());
        
        server.setExecutor(null); // creates a default executor
        server.start();
	}
	
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
	
	public static JSONArray buildDailyItemsJSON(String username, Date date, boolean checkForActive) {
		ArrayList<ScheduleItem> dailyItems = DBTools.findDailyItems(username, date);
		
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
				
				User newUser = DBTools.registerUser(username, password);
				
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
				
				User existingUser = DBTools.loginUser(username, password);
				
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
	
	static class DayViewHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling day view...");
			
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				Date date = CreateLookupDate.getInstance(headers.getFirst("Date"));
				
				JSONArray dailyItemsJSON = buildDailyItemsJSON(username, date, false);
				int dailyScore = DBTools.findDailyScore(username, date);
				
				JSONObject responseJSON = new JSONObject();
				responseJSON.put("Daily-Items", dailyItemsJSON);
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
				
				JSONArray dailyItemsCheckInJSON = buildDailyItemsJSON(username, date, true);
				
				JSONObject responseJSON = new JSONObject();
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
				
				DBTools.updateUser(user);
				
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
				Date today = user.getSchedule().getLastDayChecked();
				responseJSON.put("User-Score", user.getTotalRunningScore());
				responseJSON.put("Today-Score-So-Far", user.getSchedule().computeDailyScore(today));
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
	
}
