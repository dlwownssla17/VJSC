package db;

import java.util.Date;

import org.bson.Document;

import model.ModelTools;
import model.ScheduleItem;
import model.ScheduleItemType;
import util.DateFormat;

public class ScheduleItemDB implements DB<ScheduleItem> {
	
	@Override
	public Document toDocument(ScheduleItem item) {
		return new Document("id", item.getId())
					.append("recurrence", DBTools.scheduleItemRecurrenceDB.toDocument(item.getRecurrence()))
					.append("title", item.getTitle())
					.append("description", item.getDescription())
					.append("type", item.getType().toString())
					.append("associated-username", item.getAssociatedUsername())
					.append("created-date-time", DateFormat.getFormattedString(item.getCreatedDateTime(), ModelTools.DATE_TIME_FORMAT))
					.append("start-date-time", DateFormat.getFormattedString(item.getStartDateTime(), ModelTools.DATE_TIME_FORMAT))
					.append("progress", DBTools.progressDB.toDocument(item.getProgress()))
					.append("duration", item.getDuration())
					.append("notification-params", DBTools.scheduleItemNotificationParamsDB.toDocument(item.getNotificationParams()))
					.append("checked-in", item.isCheckedIn())
					.append("checked-in-at-start", item.isCheckedInAtStart())
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
		Date startDateTime = DateFormat.getDate(document.getString("start-date-time"), ModelTools.DATE_TIME_FORMAT);
		ScheduleItem item = new ScheduleItem(document.getLong("id"), document.getString("title"),
				document.getString("description"), type, document.getString("associated-username"),
				startDateTime, DBTools.progressDB.fromDocument((Document) document.get("progress")),
				DBTools.scheduleItemNotificationParamsDB.fromDocument((Document) document.get("notification-params")));
		item.setCreatedDateTime(DateFormat.getDate(document.getString("created-date-time"), ModelTools.DATE_TIME_FORMAT));
		item.setRecurrence(DBTools.scheduleItemRecurrenceDB.fromDocument((Document) document.get("recurrence")));
		item.setDuration(document.getInteger("duration"));
		item.setCheckedIn(document.getBoolean("checked-in"));
		item.setCheckedInAtStart(document.getBoolean("checked-in-at-start"));
		item.setScore(document.getInteger("score"));
		
		return item;
	}
	
}
