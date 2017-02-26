package model;

import java.util.Date;

public class ScheduleItem {
	private String description;
	private User associatedUser;
	private Date startDateTime;
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	
	private double yellowThreshold, greenThreshold;
	
	public ScheduleItem(String description, User associatedUser, Date startDateTime, Progress progress,
			ScheduleItemNotificationParams notificationParams, double yellowThreshold, double greenThreshold)
			throws IllegalArgumentException {
		if (description.length() > 128)
			throw new IllegalArgumentException("The description should be 128 characters or less.");
		this.description = description;
		
		this.associatedUser = associatedUser;
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
	}
	
	public ScheduleItem(String description, User associatedUser, Date startDateTime, Progress progress,
			ScheduleItemNotificationParams notificationParams) throws IllegalArgumentException {
		this(description, associatedUser, startDateTime, progress, notificationParams, 0.5, 0.8);
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
	
	public Progress getProgress() {
		return this.progress;
	}
	
	public ScheduleItemNotificationParams getNotificationParams() {
		return this.notificationParams;
	}
	
	public ScheduleItemNotificationParams setNotificationParams(ScheduleItemNotificationParams notificationParams) {
		this.notificationParams = notificationParams;
		return this.notificationParams;
	}
	
	public ScheduleItemProgressColor getProgressColor() {
		// TODO conditions for white color and grey color
		
		double progress = this.progress.getProgress();
		if (progress < this.yellowThreshold) {
			return ScheduleItemProgressColor.RED;
		} else if (this.yellowThreshold <= progress && progress < this.greenThreshold) {
			return ScheduleItemProgressColor.YELLOW;
		} else {
			return ScheduleItemProgressColor.GREEN;
		}
	}
}
