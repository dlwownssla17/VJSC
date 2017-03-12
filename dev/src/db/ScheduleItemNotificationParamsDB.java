package db;

import org.bson.Document;

import model.ScheduleItemNotificationParams;

public class ScheduleItemNotificationParamsDB implements DB<ScheduleItemNotificationParams> {
	
	@Override
	public Document toDocument(ScheduleItemNotificationParams notificationParams) {
		// TODO: implement
		return new Document();
	}
	
	@Override
	public ScheduleItemNotificationParams fromDocument(Document document) {
		// TODO: implement
		return null;
	}
	
}
