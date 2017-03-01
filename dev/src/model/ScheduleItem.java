package model;

import java.util.Date;

public class ScheduleItem {
	private long id;
	private long recurringId;
	private String title;
	private String description;
	private User associatedUser;
	private Date startDateTime;
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	private boolean able;
	private int score;
	
	private double yellowThreshold, greenThreshold;
	
	public ScheduleItem(long id, String title, String description, User associatedUser, Date startDateTime,
			Progress progress, ScheduleItemNotificationParams notificationParams,
			double yellowThreshold, double greenThreshold) throws IllegalArgumentException {
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
	}
	
	public ScheduleItem(long id, String title, String description, User associatedUser, Date startDateTime,
			Progress progress, ScheduleItemNotificationParams notificationParams) throws IllegalArgumentException {
		this(id, title, description, associatedUser, startDateTime, progress, notificationParams, 0.5, 0.8);
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
		Date acceptableRangeStart = (Date) now.clone();
		Date acceptableRangeEnd = (Date) now.clone();
		acceptableRangeStart.setTime(acceptableRangeStart.getTime() - Constant.ACTIVE_MINUTES_BEFORE * 60 * 1000);
		acceptableRangeEnd.setTime(acceptableRangeEnd.getTime() + Constant.ACTIVE_MINUTES_AFTER * 60 * 1000);
		return now.after(acceptableRangeStart) && now.before(acceptableRangeEnd);
	}
	
	public int getScore() {
		return this.score;
	}
}
