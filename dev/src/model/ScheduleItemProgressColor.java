package model;

public enum ScheduleItemProgressColor {
	WHITE("white"), RED("red"), YELLOW("yellow"), GREEN("green"), GREY("grey");
	
	private String name;
	
	private ScheduleItemProgressColor(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
