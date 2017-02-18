package util;

import java.util.Date;

public interface RecurringTime {
	public boolean happensOnDate(Date date);
	public Date firstDate();
}
