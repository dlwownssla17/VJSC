package db;

import org.bson.Document;

import model.ScheduleItem;
import model.ScheduleItemType;

public class ScheduleItemDB extends ObjectDB {
	public static Document toDocument(ScheduleItem item) {
		return new Document("id", item.getId())
					.append("recurrence", ScheduleItemRecurrenceDB.toDocument(item.getRecurrence()))
					.append("title", item.getTitle())
					.append("description", item.getDescription())
					.append("type", item.getType().toString())
					.append("associated-username", item.getAssociatedUsername())
					.append("start-date-time", item.getStartDateTime())
					.append("progress", ProgressDB.toDocument(item.getProgress()))
					.append("notification-params", ScheduleItemNotificationParamsDB.toDocument(item.getNotificationParams()))
					.append("score", item.getScore());
	}
	
	public static ScheduleItem fromDocument(Document document) {
		ScheduleItemType type = null;
		for (ScheduleItemType scheduleItemType : ScheduleItemType.values()) {
			if (scheduleItemType.toString().equals(document.getString("type"))) {
				type = scheduleItemType;
				break;
			}
		}
		ScheduleItem item = new ScheduleItem(document.getLong("id"), document.getString("title"),
				document.getString("description"), type, document.getString("username"),
				document.getDate("start-date-time"), ProgressDB.fromDocument((Document) document.get("progress")),
				ScheduleItemNotificationParamsDB.fromDocument((Document) document.get("notification-params")));
		item.setRecurrence(ScheduleItemRecurrenceDB.fromDocument((Document) document.get("recurrence")));
		item.setScore(document.getInteger("score"));
		
		return item;
	}
}
