package db;

import org.bson.Document;

import model.ScheduleItem;
import model.ScheduleItemType;

public class ScheduleItemDB implements DB<ScheduleItem> {
	
	@Override
	public Document toDocument(ScheduleItem item) {
		return new Document("id", item.getId())
					.append("recurrence", DBTools.scheduleItemRecurrenceDB.toDocument(item.getRecurrence()))
					.append("title", item.getTitle())
					.append("description", item.getDescription())
					.append("type", item.getType().toString())
					.append("associated-username", item.getAssociatedUsername())
					.append("start-date-time", item.getStartDateTime())
					.append("progress", DBTools.progressDB.toDocument(item.getProgress()))
					.append("notification-params", DBTools.scheduleItemNotificationParamsDB.toDocument(item.getNotificationParams()))
					.append("score", item.getScore());
	}
	
	@Override
	public ScheduleItem fromDocument(Document document) {
		ScheduleItemType type = null;
		for (ScheduleItemType scheduleItemType : ScheduleItemType.values()) {
			if (scheduleItemType.toString().equals(document.getString("type"))) {
				type = scheduleItemType;
				break;
			}
		}
		ScheduleItem item = new ScheduleItem(document.getLong("id"), document.getString("title"),
				document.getString("description"), type, document.getString("username"),
				document.getDate("start-date-time"), DBTools.progressDB.fromDocument((Document) document.get("progress")),
				DBTools.scheduleItemNotificationParamsDB.fromDocument((Document) document.get("notification-params")));
		item.setRecurrence(DBTools.scheduleItemRecurrenceDB.fromDocument((Document) document.get("recurrence")));
		item.setScore(document.getInteger("score"));
		
		return item;
	}
	
}
