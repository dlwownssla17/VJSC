package model;

public class ScheduleItem {
	private String description;
	private User associatedUser;
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	
	private double yellowThreshold, greenThreshold;
	
	public ScheduleItem(String description, double yellowThreshold, double greenThreshold)
			throws IllegalArgumentException {
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
	}
	
	public ScheduleItem(String description) throws IllegalArgumentException {
		this(description, 0.5, 0.8);
	}
}
