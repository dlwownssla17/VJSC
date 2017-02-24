package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.common.collect.ImmutableList;

import errors.InvalidRecurrenceException;


// TODO(vivekaraj): Improve efficiency of the getters
public class DayBasedRecurringTime implements RecurringTime {
	private List<ImmutableList<Integer>> recurrence;
	
	private boolean isValidRecurrence(List<ImmutableList<Integer>> recurrence) {
		if (recurrence == null || recurrence.isEmpty()) { return false; }
		for (ImmutableList<Integer> l : recurrence) {
			if (l.size() != 2 || l.get(0) < 0 || l.get(0) > 6 || l.get(1) < 0 || l.get(1) > 2400) {
				return false;
			}			
		}
		return true;
	}
	
	public DayBasedRecurringTime(List<ImmutableList<Integer>> recurrence) throws InvalidRecurrenceException {
		if (!isValidRecurrence(recurrence)) {
			throw new InvalidRecurrenceException();
		}
		this.recurrence = new ArrayList<>(recurrence);
	}

	@Override
	public boolean happensOnDate(Date date) {
		for (ImmutableList<Integer> l : recurrence) {
			if (l.get(0) == date.getDay()) { return true; }
		}
		return false;
	}

	@Override
	public Date firstDate() {
		Date ret = new Date(System.currentTimeMillis());
		int i = 0;
		while (i < 7) {
			if (happensOnDate(ret)) { return ret; }
			ret.setDate(ret.getDate() + 1);
		}
		
		// THIS IS BAD! THIS SHOULDN'T POSSIBLY HAPPEN
		return null;
	}

	@Override
	public boolean happensOnMonth(int month) {
		return true;
	}

	@Override
	public boolean happensOnDay(int day) {
		for (ImmutableList<Integer> l : recurrence) {
			if (l.get(0) == day) { return true; }			
		}
		return false;
	}

	@Override
	public boolean happensAtTime(int time) {
		for (ImmutableList<Integer> l : recurrence) {
			if (l.get(1) == time) { return true; }			
		}
		return false;
	}

	@Override
	public String toString() {
		return "DayBasedRecurringTime [recurrence=" + recurrence + "]";
	}

}
