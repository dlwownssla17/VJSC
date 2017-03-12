package util;

import java.util.Calendar;
import java.util.Date;

public class CreateLookupDate {
	public static Date getInstance(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return DateAndCalendar.calendarToDate(calendar);
	}
	
	public static Date getInstance(Date date) {
		Calendar calendar = DateAndCalendar.dateToCalendar(date);
		return getInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public static Date getInstance(String formattedDate) {
		int year = Integer.parseInt(formattedDate.substring(0, 4));
		int month = Integer.parseInt(formattedDate.substring(4, 6));
		int day = Integer.parseInt(formattedDate.substring(6));
		return getInstance(year, month, day);
	}
}
