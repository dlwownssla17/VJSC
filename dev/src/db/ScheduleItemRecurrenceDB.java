package db;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

import com.mongodb.BasicDBList;

import model.ScheduleItemRecurrence;

public class ScheduleItemRecurrenceDB implements DB<ScheduleItemRecurrence> {
	
	@Override
	public Document toDocument(ScheduleItemRecurrence recurrence) {
		if (recurrence == null) return new Document();
		
		return new Document("recurring-id", recurrence.getRecurringId())
					.append("recurring-type", recurrence.getRecurringType())
					.append("recurring-value", Arrays.asList(recurrence.getRecurringValue()))
					.append("start-date-time", recurrence.getStartDateTime())
					.append("end-type", recurrence.getEndType())
					.append("end-after", recurrence.getEndAfter())
					.append("end-date-time", recurrence.getEndDateTime());
	}
	
	@Override
	public ScheduleItemRecurrence fromDocument(Document document) {
		if (document.isEmpty()) return null;
		
		long recurringId = document.getLong("recurring-id");
		int recurringType = document.getInteger("recurring-type");
		BasicDBList recurringValueList = (BasicDBList) document.get("recurring-value");
		int[] recurringValue = new int[recurringValueList.size()];
		for (int i = 0; i < recurringValueList.size(); i++) {
			recurringValue[i] = (int) recurringValueList.get(i);
		}
		Date startDateTime = document.getDate("start-date-time");
		int endType = document.getInteger("end-type");
		int endAfter = document.getInteger("end-after");
		Date endDateTime = document.getDate("end-date-time");
		
		if (endType == 0) {
			return new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime);
		} else if (endType == 1) {
			return new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime, endAfter);
		} else if (endType == 2) {
			return new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime, endDateTime);
		} else {
			throw new IllegalStateException();
		}
	}
	
}
