package fitbit;

/**
 * These are the activities that are the input for full day activities.  Full
 * day activities are activities where one value is returned per day.
 * IntraDay Activities, however, are activities where values can be returned
 * per time, leading to multiple values per day.
 *
 */

public enum FitBitActivities {
	CALORIES("calories"), CALORIES_BMR("caloriesBMR"),
	STEPS("steps"), DISTANCE("distance"), FLOORS("floors"), ELEVATION("elevation"),
	MINUTES_SEDENTARY("minutesSedentary"), MINUTES_LIGHTLY_ACTIVE("minutesLightlyActive"),
	MINUTES_FAIRLY_ACTIVE("minutesFairlyActive"),
	ACTIVITY_CALORIES("activityCalories"), HEARTRATE("heart");
	
	private String name;
	
	private FitBitActivities(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
