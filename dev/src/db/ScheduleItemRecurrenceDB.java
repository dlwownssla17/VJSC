package db;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

import model.ModelTools;
import model.ScheduleItemRecurrence;
import util.DateFormat;

public class ScheduleItemRecurrenceDB implements DB<ScheduleItemRecurrence> {
	
	@Override
	public Document toDocument(ScheduleItemRecurrence recurrence) {
		if (recurrence == null) return new Document();
		
		Document recurringValueDocument = new Document();
		for (int val : recurrence.getRecurringValue()) {
			recurringValueDocument.append(Integer.toString(val), true);
		}
		
		return new Document("recurring-id", recurrence.getRecurringId())
					.append("recurring-type", recurrence.getRecurringType())
					.append("recurring-value", recurringValueDocument)
					.append("start-date-time", DateFormat.getFormattedString(recurrence.getStartDateTime(), ModelTools.DATE_TIME_FORMAT))
					.append("end-type", recurrence.getEndType())
					.append("end-after", recurrence.getEndAfter())
					.append("end-date-time", DateFormat.getFormattedString(recurrence.getEndDateTime(), ModelTools.DATE_TIME_FORMAT));
	}
	
	@Override
	public ScheduleItemRecurrence fromDocument(Document document) {
		if (document.isEmpty()) return null;
		
		long recurringId = document.getLong("recurring-id");
		int recurringType = document.getInteger("recurring-type");
		Document recurringValueList = document.get("recurring-value", Document.class);
		int[] recurringValue = new int[recurringValueList.size()];
		int recurringValueIdx = 0;
		for (String valKey : recurringValueList.keySet()) {
			recurringValue[recurringValueIdx++] = recurringValueList.getInteger(valKey);
		}
		Arrays.sort(recurringValue);
		Date startDateTime = DateFormat.getDate(document.getString("start-date-time"), ModelTools.DATE_TIME_FORMAT);
		int endType = document.getInteger("end-type");
		int endAfter = document.getInteger("end-after");
		Date endDateTime = DateFormat.getDate(document.getString("end-date-time"), ModelTools.DATE_TIME_FORMAT);
		
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
