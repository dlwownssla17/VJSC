package db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBList;

import model.ScheduleItem;
import model.UserSchedule;

public class UserScheduleDB implements DB<UserSchedule> {
	
	@Override
	public Document toDocument(UserSchedule schedule) {
		List<Document> itemsDocument = new ArrayList<>();
		HashMap<Date, ArrayList<ScheduleItem>> items = schedule.getItems();
		for (Date date : items.keySet()) {
			List<Document> dailyItemsDocument = new ArrayList<>();
			ArrayList<ScheduleItem> dailyItems = items.get(date);
			for (ScheduleItem item : dailyItems) {
				dailyItemsDocument.add(DBTools.scheduleItemDB.toDocument(item));
			}
			
			itemsDocument.add(new Document("daily-date", date)
									.append("daily-items", dailyItemsDocument));
		}
		
		List<Document> scoresDocument = new ArrayList<>();
		HashMap<Date, Integer> scores = schedule.getDailyScores();
		for (Date date : scores.keySet()) {
			int dailyScore = scores.get(date);
			
			scoresDocument.add(new Document("daily-date", date)
									.append("daily-score", dailyScore));
		}
		
		return new Document("capacity", schedule.getCapacity())
					.append("associated-username", schedule.getAssociatedUsername())
					.append("items", itemsDocument)
					.append("scores", scoresDocument)
					.append("schedule-id-counter", schedule.getScheduleIdCounter())
					.append("recurring-id-counter", schedule.getRecurringIdCounter());
	}
	
	@Override
	public UserSchedule fromDocument(Document document) {
		UserSchedule schedule = new UserSchedule(document.getInteger("capacity"),
				document.getString("associated-username"));
		
		HashMap<Date, ArrayList<ScheduleItem>> items = new HashMap<>();
		BasicDBList itemsList = (BasicDBList) document.get("items");
		for (Object obj : itemsList) {
			Document dailyItemsDocument = (Document) obj;
			Date dailyDate = dailyItemsDocument.getDate("daily-date");
			ArrayList<ScheduleItem> dailyItems = new ArrayList<>();
			BasicDBList dailyItemsList = (BasicDBList) dailyItemsDocument.get("daily-items");
			for (Object dailyObj : dailyItemsList) {
				dailyItems.add(DBTools.scheduleItemDB.fromDocument((Document) dailyObj));
			}
			items.put(dailyDate, dailyItems);
		}
		schedule.setItems(items);
		
		HashMap<Date, Integer> scores = new HashMap<>();
		BasicDBList scoresList = (BasicDBList) document.get("scores");
		for (Object obj : scoresList) {
			Document dailyScoreDocument = (Document) obj;
			Date dailyDate = dailyScoreDocument.getDate("daily-date");
			int dailyScore = dailyScoreDocument.getInteger("daily-score");
			scores.put(dailyDate, dailyScore);
		}
		schedule.setDailyScores(scores);
		
		schedule.setScheduleIdCounter(document.getLong("schedule-id-counter"));
		schedule.setRecurringIdCounter(document.getLong("recurring-id-counter"));
		
		return schedule;
	}
	
}
