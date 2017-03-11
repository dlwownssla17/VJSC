package model;

import java.util.Calendar;
import java.util.Date;

import util.DateAndCalendar;
import util.DateFormat;

// TODO: implement logic for "never ending"
// TODO: check illegal argument for recurringValue
// TODO: make code a bit more modular

public class ScheduleItemRecurrence {
	private long recurringId; // non-negative
	
	// 0 - daily, 1 - every X days, 2 - every certain days of the week, 3 - weekly, 4 - monthly
	private int recurringType;
	
	// 0 -> empty, 1 -> [X], 2 -> [0 - Sunday, ..., 6 - Saturday] (multiple), 3 -> [0 - Sunday, ..., 6 - Saturday],
	// 4 -> [day of the month]
	private int[] recurringValue;
	
	private Date startDateTime;
	
	// 0 - never, 1 - after Y occurrences, 2 - on T
	private int endType;
	
	// [Y] (if endType = 1); if set, then used to compute endDateTime
	private int endAfter; 
	
	// end date
	private Date endDateTime;
	
	private static String invalidRecurringIdMsg = "recurringId must be non-negative.";
	private static String invalidRecurringTypeMsg = "invalid recurringType.";
	private static String invalidEndTypeMsg = "invalid endType.";
	
	// never ending
	public ScheduleItemRecurrence(long recurringId, int recurringType, int[] recurringValue, Date startDateTime) {
		if (recurringId < 0) throw new IllegalArgumentException(invalidRecurringIdMsg);
		this.recurringId = recurringId;
		
		if (recurringType < 0 || recurringType > 4) throw new IllegalArgumentException(invalidRecurringTypeMsg);
		this.recurringType = recurringType;
		
		this.recurringValue = recurringValue;
		this.startDateTime = startDateTime;
		
		this.endType = 0;
		this.endAfter = -1;
		this.endDateTime = null;
	}
	
	// ending after Y occurrences
	public ScheduleItemRecurrence(long recurringId, int recurringType, int[] recurringValue, Date startDateTime,
			int endAfter) {
		this(recurringId, recurringType, recurringValue, startDateTime);
		
		this.endType = 1;
		this.endAfter = endAfter;
		this.endDateTime = this.computeEndDateTime(endAfter);
	}
	
	// ending on T
	public ScheduleItemRecurrence(long recurringId, int recurringType, int[] recurringValue, Date startDateTime,
			Date endDateTime) {
		this(recurringId, recurringType, recurringValue, startDateTime);
		
		this.endType = 2;
		this.endAfter = -1;
		this.endDateTime = endDateTime;
	}
	
	// assumes that recurringType, recurringValue, and startDateTime are already set
	private Date computeEndDateTime(int endAfter) {
		// should only be used if endType is 1 (ending after Y occurrences)
		if (this.endType != 1) return null;
		
		Calendar calendar = DateAndCalendar.dateToCalendar(this.startDateTime);
		int count = endAfter - 1;
		switch (this.recurringType) {
			case 0: // after Y days
				calendar.add(Calendar.DATE, count);
				break;
			case 1: // after X x Y days
				calendar.add(Calendar.DATE, this.recurringValue[0] * count);
				break;
			case 2:
			case 3: // after Y weeks
				calendar.add(Calendar.WEEK_OF_MONTH, count);
				break;
			case 4: // after Y months
				calendar.add(Calendar.MONTH, count);
				break;
			default:
				throw new IllegalStateException();
		}
		return DateAndCalendar.calendarToDate(calendar);
	}
	
	public long getRecurringId() {
		return this.recurringId;
	}
	
	public long setRecurringId(long recurringId) {
		if (recurringId < 0) throw new IllegalArgumentException(invalidRecurringIdMsg);
		this.recurringId = recurringId;
		return this.recurringId;
	}
	
	public int getRecurringType() {
		return this.recurringType;
	}
	
	public int setRecurringType(int recurringType) {
		if (recurringType < 0 || recurringType > 4) throw new IllegalArgumentException(invalidRecurringTypeMsg);
		this.recurringType = recurringType;
		return this.recurringType;
	}
	
	public int[] getRecurringValue() {
		return this.recurringValue;
	}
	
	public int[] setRecurringValue(int[] recurringValue) {
		this.recurringValue = recurringValue;
		return this.recurringValue;
	}
	
	public Date getStartDateTime() {
		return this.startDateTime;
	}
	
	public Date setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
		return this.startDateTime;
	}
	
	public int getEndType() {
		return this.endType;
	}
	
	public int setEndType(int endType) {
		if (endType < 0 || endType > 2) throw new IllegalArgumentException(invalidEndTypeMsg);
		this.endType = endType;
		return this.endType;
	}
	
	public int getEndAfter() {
		return this.endAfter;
	}
	
	public int setEndAfter(int endAfter) {
		this.endAfter = endAfter;
		return this.endAfter;
	}
	
	public Date getEndDateTime() {
		return this.endDateTime;
	}
	
	public Date setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
		return this.endDateTime;
	}
	
	private String numToDayOfWeek(int num) {
		switch (num) {
			case 0:
				return "Sunday";
			case 1:
				return "Monday";
			case 2:
				return "Tuesday";
			case 3:
				return "Wednesday";
			case 4:
				return "Thursday";
			case 5:
				return "Friday";
			case 6:
				return "Saturday";
			default:
				throw new IllegalStateException();
		}
	}
	
	private String recurringTypeString() {
		switch (this.recurringType) {
			case 0:
				return "daily";
			case 1:
				return "every X days";
			case 2:
				return "Every certain days of the week";
			case 3:
				return "weekly";
			case 4:
				return "monthly";
			default:
				throw new IllegalStateException();
		}
	}
	
	// depend on recurringType
	private String recurringValueString() {
		switch (this.recurringType) {
			case 0:
				return "N/A";
			case 1:
				return String.format("X = %d", this.recurringValue[0]);
			case 2:
				StringBuilder sb = new StringBuilder();
				sb.append('[');
				for (int i = 0; i < this.recurringValue.length - 1; i++) {
					sb.append(String.format("%s, ", this.numToDayOfWeek(this.recurringValue[i])));
				}
				if (this.recurringValue.length > 0)
					sb.append(this.numToDayOfWeek(this.recurringValue[this.recurringValue.length - 1]));
				sb.append(']');
				return sb.toString();
			case 3:
				return this.numToDayOfWeek(this.recurringValue[0]);
			case 4:
				return String.format("Day %d of the month", this.recurringValue[0]);
			default:
				throw new IllegalStateException();
		}
	}
	
	private String startDateTimeString() {
		return DateFormat.getFormattedString(this.startDateTime, Constant.DATE_TIME_FORMAT);
	}
	
	private String endTypeString() {
		switch (this.endType) {
			case 0:
				return "never";
			case 1:
				return "after Y occurrences";
			case 2:
				return "on T";
			default:
				throw new IllegalStateException();
		}
	}
	
	private String endValueString() {
		switch (this.endType) {
			case 0:
				return "N/A";
			case 1:
				return String.format("Y = %d", this.endAfter);
			case 2:
				return DateFormat.getFormattedString(this.endDateTime, Constant.DATE_FORMAT);
			default:
				throw new IllegalStateException();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Recurring ID: ").append(this.recurringId).append('\n');
		sb.append("Recurring Type: ").append(this.recurringTypeString()).append('\n');
		sb.append("Recurring Value: ").append(this.recurringValueString()).append('\n');
		sb.append("Start Date Time: ").append(this.startDateTimeString()).append('\n');
		sb.append("End Type: ").append(this.endTypeString()).append('\n');
		sb.append("End Value: ").append(this.endValueString()).append('\n');
		return sb.toString();
	}
}
