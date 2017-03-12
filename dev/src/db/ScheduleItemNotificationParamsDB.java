package db;

import org.bson.Document;

import model.ScheduleItemNotificationParams;

public class ScheduleItemNotificationParamsDB extends ObjectDB {
	public static Document toDocument(ScheduleItemNotificationParams notificationParams) {
		// TODO: implement
		return new Document();
	}
	
	public static ScheduleItemNotificationParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
}
