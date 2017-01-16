package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String getFormattedString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
}
