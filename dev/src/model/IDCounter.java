package model;

public class IDCounter {
	private long teamIdCounter, competitionIdCounter;
	
	public IDCounter() {
		this.teamIdCounter = 0;
		this.competitionIdCounter = 0;
	}
	
	public synchronized long getTeamIdCounter() {
		return this.teamIdCounter;
	}
	
	public synchronized long setTeamIdCounter(long teamIdCounter) {
		this.teamIdCounter = teamIdCounter;
		return this.teamIdCounter;
	}
	
	public synchronized long nextTeamIdCounter() {
		return this.teamIdCounter++;
	}
	
	public synchronized long getCompetitionIdCounter() {
		return this.competitionIdCounter;
	}
	
	public synchronized long setCompetitionIdCounter(long competitionIdCounter) {
		this.competitionIdCounter = competitionIdCounter;
		return this.competitionIdCounter;
	}
	
	public synchronized long nextCompetitionIdCounter() {
		return this.competitionIdCounter++;
	}
}
