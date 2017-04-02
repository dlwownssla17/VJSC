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
		
		return new Document("recurring-id", recurrence.getRecurringId())
					.append("recurring-type", recurrence.getRecurringType())
					.append("recurring-value", this.recurringValueToDocument(recurrence))
					.append("start-date-time", DateFormat.getFormattedString(recurrence.getStartDateTime(),
																						ModelTools.DATE_TIME_FORMAT))
					.append("end-type", recurrence.getEndType())
					.append("end-after", recurrence.getEndAfter())
					.append("end-date-time", DateFormat.getFormattedString(recurrence.getEndDateTime(),
																						ModelTools.DATE_TIME_FORMAT));
	}
	
	@Override
	public ScheduleItemRecurrence fromDocument(Document document) {
		if (document.isEmpty()) return null;
		
		long recurringId = document.getLong("recurring-id");
		int recurringType = document.getInteger("recurring-type");
		int[] recurringValue = this.recurringValueFromDocument(document);
		Date startDateTime = DateFormat.getDate(document.getString("start-date-time"), ModelTools.DATE_TIME_FORMAT);
		int endType = document.getInteger("end-type");
		int endAfter = document.getInteger("end-after");
		Date endDateTime = DateFormat.getDate(document.getString("end-date-time"), ModelTools.DATE_TIME_FORMAT);
		
		return endType == 0 ?
				new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime) :
				endType == 1 ?
				new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime, endAfter) :
				endType == 2 ?
				new ScheduleItemRecurrence(recurringId, recurringType, recurringValue, startDateTime, endDateTime) : null;
	}
	
	/* * */
	
	public Document recurringValueToDocument(ScheduleItemRecurrence recurrence) {
		Document recurringValueDocument = new Document();
		for (int val : recurrence.getRecurringValue()) {
			recurringValueDocument.append(Integer.toString(val), true);
		}
		return recurringValueDocument;
	}
	
	public int[] recurringValueFromDocument(Document document) {
		Document recurringValueList = document.get("recurring-value", Document.class);
		int[] recurringValue = new int[recurringValueList.size()];
		int recurringValueIdx = 0;
		for (String valKey : recurringValueList.keySet()) {
			recurringValue[recurringValueIdx++] = recurringValueList.getInteger(valKey);
		}
		Arrays.sort(recurringValue);
		return recurringValue;
	}
	
}
