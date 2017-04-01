package model;

public enum CompetitionTeamColor {
	RED("red"), BLUE("blue");
	
	private String name;
	
	private CompetitionTeamColor(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
