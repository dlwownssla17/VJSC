package model;

public enum ProgressColor {
	WHITE("white"), RED("red"), YELLOW("yellow"), GREEN("green"), GREY("grey");
	
	private String name;
	
	private ProgressColor(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
