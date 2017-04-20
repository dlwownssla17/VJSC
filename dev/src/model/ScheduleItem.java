package model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import util.DateAndCalendar;

public class ScheduleItem {
	private long id; // unique, non-negative
	private ScheduleItemRecurrence recurrence;
	private String title;
	private String description;
	private ScheduleItemType type;
	private String associatedUsername;
	private Date createdDateTime;
	private Date startDateTime;
	private Progress progress;
	private int duration; // relevant to percentage type progress; in minutes
	
	private ScheduleItemNotificationParams notificationParams;
	
	// private boolean able; // the check-in time hasn't passed yet
	private boolean checkedIn;
	private boolean checkedInAtStart; // relevant to percentage type progress
	private int score;
	
	private double yellowThreshold, greenThreshold;
	
	public ScheduleItem(long id, String title, String description, ScheduleItemType type, String associatedUsername,
			Date startDateTime, Progress progress, ScheduleItemNotificationParams notificationParams,
			double yellowThreshold, double greenThreshold) throws IllegalArgumentException {
		if (id < 0) throw new IllegalArgumentException("The schedule id must be non-negative.");
		this.id = id;
		
		if (title.length() > 128)
			throw new IllegalArgumentException("The title should be 128 characters or less.");
		this.title = title;
		
		if (description.length() > 1024)
			throw new IllegalArgumentException("The description should be 1024 characters or less.");
		this.description = description;
		
		this.type = type;
		this.associatedUsername = associatedUsername;
		
		this.createdDateTime = DateAndCalendar.newDateGMT();
		/*
		if (startDateTime.before(this.createdDateTime))
			throw new IllegalArgumentException("The start date time cannot be before now.");
			*/
		this.startDateTime = startDateTime;
		
		this.progress = progress;
		this.notificationParams = notificationParams;
		
		// this.able = true;
		this.checkedIn = false;
		this.checkedInAtStart = false;
		this.score = 0;
		
		if (yellowThreshold < 0. || yellowThreshold > 0.999)
			throw new IllegalArgumentException("The yellow threshold must be between 0% and 99.9%.");
		if (greenThreshold < 0.001 || greenThreshold > 1.)
			throw new IllegalArgumentException("The green threshold must be between 0.1% and 100%.");
		if (yellowThreshold >= greenThreshold)
			throw new IllegalArgumentException("The yellow threshold must be below the green threshold.");
		this.yellowThreshold = yellowThreshold;
		this.greenThreshold = greenThreshold;
	}
	
	public ScheduleItem(long id, String title, String description, ScheduleItemType type, String associatedUsername,
			Date startDateTime, Progress progress, ScheduleItemNotificationParams notificationParams)
					throws IllegalArgumentException {
		this(id, title, description, type, associatedUsername, startDateTime, progress, notificationParams,
				ModelTools.DEFAULT_YELLOW_THRESHOLD, ModelTools.DEFAULT_GREEN_THRESHOLD);
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
	
	public ScheduleItemRecurrence getRecurrence() {
		return this.recurrence;
	}
	
	public ScheduleItemRecurrence setRecurrence(ScheduleItemRecurrence recurrence) {
		this.recurrence = recurrence;
		return this.recurrence;
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
	
	public ScheduleItemType getType() {
		return this.type;
	}
	
	public String getAssociatedUsername() {
		return this.associatedUsername;
	}
	
	public Date getCreatedDateTime() {
		return this.createdDateTime;
	}
	
	public Date setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
		return this.createdDateTime;
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
	
	public ProgressColor getProgressColor() {
		// TODO condition for white and grey colors
		
		double progress = this.progress.getProgress();
		if (progress < this.yellowThreshold) return ProgressColor.RED;
		else if (progress >= this.greenThreshold) return ProgressColor.GREEN;
		return ProgressColor.YELLOW;
	}
	
	// only relevant for percentage type progress
	public int getDuration() {
		return this.duration;
	}
	
	// called for percentage type progress
	public int setDuration(int duration) {
		this.duration = duration;
		return this.duration;
	}
	
	public ScheduleItemNotificationParams getNotificationParams() {
		return this.notificationParams;
	}
	
	public ScheduleItemNotificationParams setNotificationParams(ScheduleItemNotificationParams notificationParams) {
		this.notificationParams = notificationParams;
		return this.notificationParams;
	}
	
	/*
	public void checkAble() {
		return true;
	}
	*/
	
	// check-in is possible right now
	public boolean isActive() {
		if (this.checkedIn) return false;
		
		Calendar checkInDateTime = DateAndCalendar.dateToCalendar(this.startDateTime);
		checkInDateTime.setTimeZone(TimeZone.getDefault());
		int activeMinutesBefore = ModelTools.START_ACTIVE_MINUTES_BEFORE;
		int activeMinutesAfter = ModelTools.START_ACTIVE_MINUTES_AFTER;
		
		if (this.progress instanceof PercentageProgress) {
			if (this.checkedInAtStart) {
				checkInDateTime.add(Calendar.MINUTE, this.duration);
				activeMinutesBefore = Math.min(ModelTools.END_ACTIVE_MINUTES_BEFORE,
						(int) Math.round((double) this.duration * ModelTools.END_ACTIVE_MINUTES_BEFORE /
								(ModelTools.START_ACTIVE_MINUTES_AFTER + ModelTools.END_ACTIVE_MINUTES_BEFORE)));
				activeMinutesAfter = ModelTools.END_ACTIVE_MINUTES_AFTER;
			} else {
				activeMinutesAfter = Math.min(ModelTools.START_ACTIVE_MINUTES_AFTER,
						(int) Math.round((double) this.duration * ModelTools.START_ACTIVE_MINUTES_AFTER /
								(ModelTools.START_ACTIVE_MINUTES_AFTER + ModelTools.END_ACTIVE_MINUTES_BEFORE)));
			}
		} else if (this.progress instanceof LevelsProgress) { // not implementing LevelsProgress yet
			return false;
		}
		
		Calendar acceptableRangeStart = (Calendar) checkInDateTime.clone();
		acceptableRangeStart.add(Calendar.MINUTE, -activeMinutesBefore);
		Date acceptableRangeStartDateTime = DateAndCalendar.calendarToDate(acceptableRangeStart);
		
		Calendar acceptableRangeEnd = (Calendar) checkInDateTime.clone();
		acceptableRangeEnd.add(Calendar.MINUTE, activeMinutesAfter);
		Date acceptableRangeEndDateTime = DateAndCalendar.calendarToDate(acceptableRangeEnd);
		
		Date now = DateAndCalendar.newDateGMT();
		return now.after(acceptableRangeStartDateTime) && now.before(acceptableRangeEndDateTime);
	}
	
	// edit/remove is possible right now
	public boolean isModifiable() {
		if (this.checkedIn || this.checkedInAtStart) return false;
		
		Calendar modifiableAfterCreated = DateAndCalendar.dateToCalendar(this.createdDateTime);
		modifiableAfterCreated.add(Calendar.MINUTE, ModelTools.MODIFIABLE_AFTER_CREATE);
		
		Calendar now = DateAndCalendar.newCalendarGMT();
		if (now.before(modifiableAfterCreated)) return true;
		
		Calendar notModifiableBeforeStart = DateAndCalendar.dateToCalendar(this.startDateTime);
		notModifiableBeforeStart.add(Calendar.MINUTE, -ModelTools.NOT_MODIFIABLE_BEFORE_START);
		
		return now.before(notModifiableBeforeStart);
	}
	
	public boolean isCheckedIn() {
		return this.checkedIn;
	}
	
	public boolean setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
		return this.checkedIn;
	}
	
	public boolean isCheckedInAtStart() {
		return this.checkedInAtStart;
	}
	
	public boolean setCheckedInAtStart(boolean checkedInAtStart) {
		this.checkedInAtStart = checkedInAtStart;
		return this.checkedInAtStart;
	}
	
	public synchronized int checkInAtEnd(double progress) {
		this.checkedIn = true;
		return this.updateScore(progress);
	}
	
	public synchronized int checkInAtStart() {
		this.checkedInAtStart = true;
		return 0;
	}
	
	public synchronized int checkIn(double progress) {
		// not implementing LevelsProgress yet
		return this.progress instanceof BooleanProgress ||
				(this.progress instanceof PercentageProgress && this.checkedInAtStart) ? checkInAtEnd(progress) :
					(this.progress instanceof PercentageProgress ? checkInAtStart() : -1);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int setScore(int score) {
		this.score = score;
		return this.score;
	}
	
	// TODO: Improve schedule item level scoring algorithm
	private int updateScore(double progress) {
		this.progress.setProgress(progress);
		this.score = (int) Math.round(this.type.scoreBaseByScheduleItemType() * progress);
		return this.score;
	}

}
