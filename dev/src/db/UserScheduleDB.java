package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBList;

import model.ModelTools;
import model.ScheduleItem;
import model.UserSchedule;
import util.DateFormat;

public class UserScheduleDB implements DB<UserSchedule> {
	
	@Override
	public Document toDocument(UserSchedule schedule) {
		Document itemsDocument = new Document();
		HashMap<Date, ArrayList<ScheduleItem>> items = schedule.getItems();
		for (Date date : items.keySet()) {
			Document dailyItemsDocument = new Document();
			ArrayList<ScheduleItem> dailyItems = items.get(date);
			for (ScheduleItem item : dailyItems) {
				dailyItemsDocument.append(Long.toString(item.getId()), DBTools.scheduleItemDB.toDocument(item));
			}
			
			itemsDocument.append(DateFormat.getFormattedString(date, ModelTools.DATE_FORMAT),
									new Document("daily-date", date)
										.append("daily-items", dailyItemsDocument));
		}
		
		Document scoresDocument = new Document();
		HashMap<Date, Integer> scores = schedule.getDailyScores();
		HashMap<Date, Integer> runningScores = schedule.getDailyRunningScores();
		for (Date date : scores.keySet()) {
			int dailyScore = scores.get(date);
			int dailyRunningScore = runningScores.get(date);
			
			scoresDocument.append(DateFormat.getFormattedString(date, ModelTools.DATE_FORMAT),
									new Document("daily-date", date)
										.append("daily-score", dailyScore)
										.append("daily-running-score", dailyRunningScore));
		}
		
		return new Document("capacity", schedule.getCapacity())
					.append("associated-username", schedule.getAssociatedUsername())
					.append("items", itemsDocument)
					.append("scores", scoresDocument)
					.append("schedule-id-counter", schedule.getScheduleIdCounter())
					.append("recurring-id-counter", schedule.getRecurringIdCounter())
					.append("last-day-checked",
							DateFormat.getFormattedString(schedule.getLastDayChecked(), ModelTools.DATE_FORMAT));
	}
	
	@Override
	public UserSchedule fromDocument(Document document) {
		UserSchedule schedule = new UserSchedule(document.getInteger("capacity"),
				DateFormat.getDate(document.getString("last-day-checked"), ModelTools.DATE_FORMAT),
				document.getString("associated-username"));
		
		HashMap<Date, ArrayList<ScheduleItem>> items = new HashMap<>();
		Document itemsList = document.get("items", Document.class);
		for (String dailyItemsDocumentKey : itemsList.keySet()) {
			Document dailyItemsDocument = itemsList.get(dailyItemsDocumentKey, Document.class);
			Date dailyDate = dailyItemsDocument.getDate("daily-date");
			ArrayList<ScheduleItem> dailyItems = new ArrayList<>();
			Document dailyItemsList = dailyItemsDocument.get("daily-items", Document.class);
			for (String dailyObjKey : dailyItemsList.keySet()) {
				Document dailyObj = dailyItemsList.get(dailyObjKey, Document.class);
				dailyItems.add(DBTools.scheduleItemDB.fromDocument(dailyObj));
			}
			items.put(dailyDate, dailyItems);
		}
		schedule.setItems(items);
		
		HashMap<Date, Integer> scores = new HashMap<>();
		HashMap<Date, Integer> runningScores = new HashMap<>();
		Document scoresList = document.get("scores", Document.class);
		for (String dailyScoreDocumentKey : scoresList.keySet()) {
			Document dailyScoreDocument = scoresList.get(dailyScoreDocumentKey, Document.class);
			Date dailyDate = dailyScoreDocument.getDate("daily-date");
			int dailyScore = dailyScoreDocument.getInteger("daily-score");
			int dailyRunningScore = dailyScoreDocument.getInteger("daily-running-score");
			scores.put(dailyDate, dailyScore);
			runningScores.put(dailyDate, dailyRunningScore);
		}
		schedule.setDailyScores(scores);
		schedule.setDailyRunningScores(runningScores);
		
		schedule.setScheduleIdCounter(document.getLong("schedule-id-counter"));
		schedule.setRecurringIdCounter(document.getLong("recurring-id-counter"));
		schedule.setLastDayChecked(DateFormat.getDate(document.getString("last-day-checked"), ModelTools.DATE_FORMAT));
		
		return schedule;
	}
	
}
