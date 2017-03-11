package model;

public enum ScheduleItemType {
	EATING("eating"), EXERCISE("exercise"), MEDICATION("medication"), BG_MEASUREMENT("blood_glucose_measurement");
	
	private String name;
	
	private ScheduleItemType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int scoreBaseByScheduleItemType() {
		switch(this) {
			case EATING:
				return 50;
			case EXERCISE:
				return 100;
			case MEDICATION:
				return 20;
			case BG_MEASUREMENT:
				return 200;
			default:
				throw new IllegalStateException();
		}
	}
}
