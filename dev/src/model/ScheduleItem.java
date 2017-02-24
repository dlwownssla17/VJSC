package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import util.RecurringTime;

public class ScheduleItem implements Comparable<ScheduleItem> {	
	public static final String UNIQUE_ID_FILE_NAME = "max_schedule_id";
	public static final double DEFAULT_YELLOW_THRESHOLD = .5;
	public static final double DEFAULT_GREEN_THRESHOLD = .8;

	int id;
	private String description;
	private User user;
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	private Set<RecurringTime> recurringTimes;
	
	private double yellowThreshold, greenThreshold;	
	
	synchronized int setID() {
		try {
			File file = new File(UNIQUE_ID_FILE_NAME);
			Scanner sc = new Scanner(file);
			int ret = sc.nextInt();
			sc.close();
			file = new File(UNIQUE_ID_FILE_NAME);
			FileWriter write = new FileWriter(file);
			write.write("" + (ret+1));
			write.close();
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new RuntimeException("Error in setting ID for schedule item");
	}
	
	/*
	 * Vivek: @JJ, why did you include this?
	public ScheduleItem(String description, double yellowThreshold, double greenThreshold) {
		if (description.length() > 128)
			throw new IllegalArgumentException("The description should be 128 characters or less.");
		this.description = description;
		
		if (yellowThreshold < 0. || yellowThreshold > 0.999)
			throw new IllegalArgumentException("The yellow threshold must be between 0% and 99.9%.");
		if (greenThreshold < 0.001 || greenThreshold > 1.)
			throw new IllegalArgumentException("The green threshold must be between 0.1% and 100%.");
		if (yellowThreshold >= greenThreshold)
			throw new IllegalArgumentException("The yellow threshold must be below the green threshold.");
		this.yellowThreshold = yellowThreshold;
		this.greenThreshold = greenThreshold;
		id = setID();
	}
	*/
	
	public ScheduleItem(User user, String description, Set<RecurringTime> recurringTimes) {
		this.id = setID();
		this.yellowThreshold = DEFAULT_YELLOW_THRESHOLD;
		this.greenThreshold = DEFAULT_GREEN_THRESHOLD;
		this.user = user;
		this.description = description;
		this.recurringTimes = recurringTimes;
	}
	
	public int getID() { return id; }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleItem other = (ScheduleItem) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean happensOnDate(Date d) {
		for (RecurringTime rt : recurringTimes) {
			if (rt.happensOnDate(d)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(ScheduleItem o) {
		if (recurringTimes.isEmpty() && o.recurringTimes.isEmpty()) {
			return 0;
		} else if (recurringTimes.isEmpty()) {
			return -1;
		} else if (o.recurringTimes.isEmpty()) {
			return 1;
		}
		
		// Get earliest date for recurring times 1
		Date d1 = recurringTimes.iterator().next().firstDate();
		for (RecurringTime rt : recurringTimes) {
			Date d = rt.firstDate();
			if (d1.compareTo(d) > 0) { d1 = d; }
		}
		
		// Get earliest date for recurring times 2
		Date d2 = o.recurringTimes.iterator().next().firstDate();
		for (RecurringTime rt : o.recurringTimes) {
			Date d = rt.firstDate();
			if (d2.compareTo(d) > 0) { d2 = d; }
		}
		System.out.println(d1.toString());
		System.out.println(d2.toString());
		return d1.compareTo(d2);
	}
	
	
	
	private static class Test implements RecurringTime {
		DayOfWeek day;
		
		Test(DayOfWeek day) {
			this.day = day;
		}

		@Override
		public boolean happensOnDate(Date date) {
			return date.getDay() == day.getValue() % 7;
		}

		@Override
		public Date firstDate() {
			Date d = new Date(System.currentTimeMillis());
			while (!happensOnDate(d)) {
				d.setDate(d.getDate()+1);
			}
			return d;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((day == null) ? 0 : day.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Test other = (Test) obj;
			if (day != other.day)
				return false;
			return true;
		}
		
	}
	
	public static void main(String[] args) {

		Set<RecurringTime> rt1 = new HashSet<>();
		rt1.add(new Test(DayOfWeek.MONDAY));
		rt1.add(new Test(DayOfWeek.WEDNESDAY));

		ScheduleItem item1 = new ScheduleItem(null, null, rt1);
		
		Set<RecurringTime> rt2 = new HashSet<>();
		rt2.add(new Test(DayOfWeek.SUNDAY));
		rt2.add(new Test(DayOfWeek.SUNDAY));
		
		ScheduleItem item2 = new ScheduleItem(null, null, rt2);
		System.out.println(item1.compareTo(item2));
	}
	
	
	
	
}
