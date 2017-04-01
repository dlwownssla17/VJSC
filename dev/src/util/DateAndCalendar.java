package util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateAndCalendar {
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	public static Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}
	
	public static Date addDate(Date date, int day) {
		Calendar calendar = dateToCalendar(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendarToDate(calendar);
	}
	
	public static Date newDateGMT() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		return calendar.getTime();
	}
}
