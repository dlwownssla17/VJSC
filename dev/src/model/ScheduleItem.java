package model;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import util.Util;

public class ScheduleItem {
	private long id;
	private long recurringId;
	private String title;
	private String description;
	private User associatedUser;
	private Date startDateTime;
	private Date endDateTime; // can be null
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	private boolean able;
	private int category;
	
	private static ImmutableMap<Integer, Integer> categoryToScore = getCategoryToScore();
	
	private static ImmutableMap<Integer, Integer> getCategoryToScore() {		
		try {
			File file = new File("categoryToScore.txt");
			if (!file.exists()) {
				System.err.println("categoryToScore file doesn't exist :O");	
				return ImmutableMap.<Integer,Integer>builder().build(); // empty map
			}
			
			Scanner sc = new Scanner(file);			
			sc.nextLine(); // remove header line
			
			ImmutableMap.Builder<Integer,Integer> builder = ImmutableMap.<Integer,Integer>builder();
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				String[] arr = line.split("//");
				
				if (arr.length != 3) {
					System.err.println("categoryToScore -- each line must contain 3 entries: " + line);	
					continue; 
				}
				
				for (int i = 0; i < arr.length; i++) {
					arr[i] = arr[i].trim();
				}
				if (!Util.isNumeric(arr[1]) || !Util.isNumeric(arr[2])) {
					System.err.println("categoryToScore contains a non-numeric id or score at line: " + line);	
					continue; 
				}
				
				builder.put(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));				
			}
			
			sc.close();
			return builder.build();
		} catch (IOException e) {
			e.printStackTrace();
			return ImmutableMap.<Integer,Integer>builder().build(); // empty map
		}
	}
	
	private ImmutableList<Boolean> daysOfWeekActive; // index 0 = SUNDAY, index 1 = MONDAY, ..., index 6 = SATURDAY
	
	private double yellowThreshold, greenThreshold;
	
	// @vivekaraj: @JJ, is 2 constructors necessary? I prefer not having code duplication.
	public ScheduleItem(
		long id, 
		String title, 
		String description, 
		User associatedUser, 
		Date startDateTime,
		Date endDateTime,
		ImmutableList<Boolean> daysOfWeekActive,
		Progress progress, 
		ScheduleItemNotificationParams notificationParams,
		double yellowThreshold, 
		double greenThreshold
	) throws IllegalArgumentException {
		this.id = id;
		
		if (title.length() > 128)
			throw new IllegalArgumentException("The title should be 128 characters or less.");
		
		if (description.length() > 1024)
			throw new IllegalArgumentException("The description should be 1024 characters or less.");
		this.description = description;
		
		this.associatedUser = associatedUser;
		
		if (startDateTime.before(new Date()))
			throw new IllegalArgumentException("The start date time cannot be before now.");
		this.startDateTime = startDateTime;
		
		this.progress = progress;
		this.notificationParams = notificationParams;
		
		if (yellowThreshold < 0. || yellowThreshold > 0.999)
			throw new IllegalArgumentException("The yellow threshold must be between 0% and 99.9%.");
		if (greenThreshold < 0.001 || greenThreshold > 1.)
			throw new IllegalArgumentException("The green threshold must be between 0.1% and 100%.");
		if (yellowThreshold >= greenThreshold)
			throw new IllegalArgumentException("The yellow threshold must be below the green threshold.");
		this.yellowThreshold = yellowThreshold;
		this.greenThreshold = greenThreshold;
		
		this.endDateTime = endDateTime;
		
		if (
			daysOfWeekActive == null 
			|| daysOfWeekActive.size() != 7 
			|| Util.containsNull(daysOfWeekActive)
		) {
			throw new IllegalArgumentException("ScheduleItem constructor: invalid daysOfWeekActive");
		}
		
		this.daysOfWeekActive = daysOfWeekActive;
		
	}
	
	public ScheduleItem(
		long id, 
		String title, 
		String description, 
		User associatedUser, 
		Date startDateTime,
		Date endDateTime,
		ImmutableList<Boolean> daysOfWeekActive,
		Progress progress, 
		ScheduleItemNotificationParams notificationParams
	) throws IllegalArgumentException {
		this(id, title, description, associatedUser, startDateTime, endDateTime, daysOfWeekActive, progress, notificationParams, 0.5, 0.8);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ScheduleItem)) return false;
		return this.id == ((ScheduleItem) o).id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public long setId(long id) {
		this.id = id;
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String setTitle(String title) {
		this.title = title;
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String setDescription(String description) {
		this.description = description;
		return this.description;
	}
	
	public User getAssociatedUser() {
		return this.associatedUser;
	}
	
	public Date getStartDateTime() {
		return this.startDateTime;
	}
	
	public Date setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
		return this.startDateTime;
	}
	
	public double getProgress() {
		return this.progress.getProgress();
	}
	
	public ScheduleItemProgressColor getProgressColor() {
		// TODO condition for white color
		
		double progress = this.progress.getProgress();
		if (progress < this.yellowThreshold) return ScheduleItemProgressColor.RED;
		else if (progress >= this.greenThreshold) return ScheduleItemProgressColor.GREEN;
		return ScheduleItemProgressColor.YELLOW;
	}
	
	public ScheduleItemNotificationParams getNotificationParams() {
		return this.notificationParams;
	}
	
	public ScheduleItemNotificationParams setNotificationParams(ScheduleItemNotificationParams notificationParams) {
		this.notificationParams = notificationParams;
		return this.notificationParams;
	}
	
	private boolean isActive() {
		Date now = new Date();
		
		if (!daysOfWeekActive.get(now.getDay())) {
			return false;
		}
		
		if (endDateTime != null && now.before(endDateTime)) {
			return false;
		}
		
		Date acceptableRangeStart = (Date) now.clone();
		Date acceptableRangeEnd = (Date) now.clone();
		acceptableRangeStart.setTime(acceptableRangeStart.getTime() - Constant.ACTIVE_MINUTES_BEFORE * 60 * 1000);
		acceptableRangeEnd.setTime(acceptableRangeEnd.getTime() + Constant.ACTIVE_MINUTES_AFTER * 60 * 1000);
		return now.after(acceptableRangeStart) && now.before(acceptableRangeEnd);
	}
	
	public int getScore() {
		return categoryToScore.get(category);
	}
}
