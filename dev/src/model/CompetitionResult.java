package model;

public enum CompetitionResult {
	WON("won"), TIED("tied"), LOST("lost");
	
	private String name;
	
	private CompetitionResult(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
