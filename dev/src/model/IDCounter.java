package model;

public class IDCounter {
	private long teamIdCounter, competitionIdCounter;
	
	public IDCounter() {
		this.teamIdCounter = 0;
		this.competitionIdCounter = 0;
	}
	
	synchronized public long getTeamIdCounter() {
		return this.teamIdCounter;
	}
	
	synchronized public long setTeamIdCounter(long teamIdCounter) {
		this.teamIdCounter = teamIdCounter;
		return this.teamIdCounter;
	}
	
	synchronized public long nextTeamIdCounter() {
		return this.teamIdCounter++;
	}
	
	synchronized public long getCompetitionIdCounter() {
		return this.competitionIdCounter;
	}
	
	synchronized public long setCompetitionIdCounter(long competitionIdCounter) {
		this.competitionIdCounter = competitionIdCounter;
		return this.competitionIdCounter;
	}
	
	synchronized public long nextCompetitionIdCounter() {
		return this.competitionIdCounter++;
	}
}
