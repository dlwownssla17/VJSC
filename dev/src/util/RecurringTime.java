package util;

import java.util.Date;

public interface RecurringTime {
	public boolean happensOnDate(Date date);
	public Date firstDate();
	
	/*
	 * January = 1, February = 2, ... , December = 12
	 */
	public boolean happensOnMonth(int month);
	
	/*
	 * Sunday = 0, Monday = 1, ..., Saturday = 6.
	 */
	public boolean happensOnDay(int day);
	
	/* 
	 * time ranges from 0 to 2400.
	 */
	public boolean happensAtTime(int time);
}
