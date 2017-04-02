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
		return new Document("capacity", schedule.getCapacity())
					.append("associated-username", schedule.getAssociatedUsername())
					.append("items", this.itemsToDocument(schedule))
					.append("scores", this.scoresToDocument(schedule))
					.append("schedule-id-counter", schedule.getScheduleIdCounter())
					.append("recurring-id-counter", schedule.getRecurringIdCounter())
					.append("last-day-checked", DateFormat.getFormattedString(schedule.getLastDayChecked(),
																							ModelTools.DATE_FORMAT));
	}
	
	@Override
	public UserSchedule fromDocument(Document document) {
		UserSchedule schedule = new UserSchedule(document.getInteger("capacity"),
				DateFormat.getDate(document.getString("last-day-checked"), ModelTools.DATE_FORMAT),
				document.getString("associated-username"));
		
		schedule.setItems(this.itemsFromDocument(document));
		schedule.setDailyScores(this.scoresFromDocument(document));
		schedule.setDailyRunningScores(this.runningScoresFromDocument(document));
		schedule.setScheduleIdCounter(document.getLong("schedule-id-counter"));
		schedule.setRecurringIdCounter(document.getLong("recurring-id-counter"));
		schedule.setLastDayChecked(DateFormat.getDate(document.getString("last-day-checked"), ModelTools.DATE_FORMAT));
		
		return schedule;
	}
	
	/* * */
	
	public Document itemsToDocument(UserSchedule schedule) {
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
		return itemsDocument;
	}
	
	public HashMap<Date, ArrayList<ScheduleItem>> itemsFromDocument(Document document) {
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
		return items;
	}
	
	public Document scoresToDocument(UserSchedule schedule) {
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
		return scoresDocument;
	}
	
	public HashMap<Date, Integer> scoresFromDocument(Document document) {
		HashMap<Date, Integer> scores = new HashMap<>();
		Document scoresList = document.get("scores", Document.class);
		for (String dailyScoreDocumentKey : scoresList.keySet()) {
			Document dailyScoreDocument = scoresList.get(dailyScoreDocumentKey, Document.class);
			Date dailyDate = dailyScoreDocument.getDate("daily-date");
			int dailyScore = dailyScoreDocument.getInteger("daily-score");
			scores.put(dailyDate, dailyScore);
		}
		return scores;
	}
	
	public HashMap<Date, Integer> runningScoresFromDocument(Document document) {
		HashMap<Date, Integer> runningScores = new HashMap<>();
		Document scoresList = document.get("scores", Document.class);
		for (String dailyScoreDocumentKey : scoresList.keySet()) {
			Document dailyScoreDocument = scoresList.get(dailyScoreDocumentKey, Document.class);
			Date dailyDate = dailyScoreDocument.getDate("daily-date");
			int dailyRunningScore = dailyScoreDocument.getInteger("daily-running-score");
			runningScores.put(dailyDate, dailyRunningScore);
		}
		return runningScores;
	}
	
}
