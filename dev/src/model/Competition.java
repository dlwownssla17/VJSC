package model;

import java.util.Date;

import util.CreateLookupDate;

public class Competition {
	private String competitionName;
	private long competitionId;
	
	private Date competitionStartDate, competitionEndDate;
	
	private Team teamRed, teamBlue;
	private int teamRedScore, teamBlueScore;
	private boolean teamRedLeft, teamBlueLeft;
	
	private boolean showTeamMembers;
	private boolean status; // true - active, false - pending
	
	public Competition(String competitionName, long competitionId, Date competitionStartDate, Date competitionEndDate,
			Team teamRed, Team teamBlue, boolean showTeamMembers) {
		this.competitionName = competitionName;
		this.competitionId = competitionId;
		
		if (CreateLookupDate.getInstance(new Date()).after(competitionStartDate) ||
				competitionStartDate.after(competitionEndDate))
			throw new IllegalArgumentException("Invalid start/end dates.");
		this.competitionStartDate = competitionStartDate;
		this.competitionEndDate = competitionEndDate;
		
		if (teamRed.equals(teamBlue)) throw new IllegalArgumentException("Teams must be different.");
		this.teamRed = teamRed;
		this.teamBlue = teamBlue;
		this.teamRedScore = 0;
		this.teamBlueScore = 0;
		this.teamRedLeft = false;
		this.teamBlueLeft = false;
		
		this.showTeamMembers = showTeamMembers;
		this.status = false;
	}
	
	public String getCompetitionName() {
		return this.competitionName;
	}
	
	public String setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
		return this.competitionName;
	}
	
	public long getCompetitionId() {
		return this.competitionId;
	}
	
	public long setCompetitionId(long competitionId) {
		this.competitionId = competitionId;
		return this.competitionId;
	}
	
	public Date getCompetitionStartDate() {
		return this.competitionStartDate;
	}
	
	public Date setCompetitionStartDate(Date competitionStartDate) {
		this.competitionStartDate = competitionStartDate;
		return this.competitionStartDate;
	}
	
	public Date getCompetitionEndDate() {
		return this.competitionEndDate;
	}
	
	public Date setCompetitionEndDate(Date competitionEndDate) {
		this.competitionEndDate = competitionEndDate;
		return this.competitionEndDate;
	}
	
	public Team getTeamRed() {
		return this.teamRed;
	}
	
	public Team setTeamRed(Team teamRed) {
		this.teamRed = teamRed;
		return this.teamRed;
	}
	
	public Team getTeamBlue() {
		return this.teamBlue;
	}
	
	public Team setTeamBlue(Team teamBlue) {
		this.teamBlue = teamBlue;
		return this.teamBlue;
	}
	
	synchronized public int getTeamRedScore() {
		return this.teamRedScore;
	}
	
	synchronized public int setTeamRedScore(int teamRedScore) {
		this.teamRedScore = teamRedScore;
		return this.teamRedScore;
	}
	
	synchronized public int addTeamRedScore(int teamRedScoreToAdd) {
		this.teamRedScore += teamRedScoreToAdd;
		return this.teamRedScore;
	}
	
	synchronized public int getTeamBlueScore() {
		return this.teamBlueScore;
	}
	
	synchronized public int setTeamBlueScore(int teamBlueScore) {
		this.teamBlueScore = teamBlueScore;
		return this.teamBlueScore;
	}
	
	synchronized public int addTeamBlueScore(int teamBlueScoreToAdd) {
		this.teamBlueScore += teamBlueScoreToAdd;
		return this.teamBlueScore;
	}
	
	public boolean getTeamRedLeft() {
		return this.teamRedLeft;
	}
	
	public boolean setTeamRedLeft(boolean teamRedLeft) {
		this.teamRedLeft = teamRedLeft;
		return this.teamRedLeft;
	}
	
	public boolean getTeamBlueLeft() {
		return this.teamBlueLeft;
	}
	
	public boolean setTeamBlueLeft(boolean teamBlueLeft) {
		this.teamBlueLeft = teamBlueLeft;
		return this.teamBlueLeft;
	}
	
	public boolean getShowTeamMembers() {
		return this.showTeamMembers;
	}
	
	public boolean setShowTeamMembers(boolean showTeamMembers) {
		this.showTeamMembers = showTeamMembers;
		return this.showTeamMembers;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public boolean setStatus(boolean status) {
		this.status = status;
		return this.status;
	}
	
	public CompetitionInvitation toCompetitionInvitation(Team team) {
		if (!(this.teamRed.equals(team) || this.teamBlue.equals(team)))
			throw new IllegalArgumentException("Invalid team.");
		return new CompetitionInvitation(this.competitionName, this.competitionId, this.competitionStartDate,
				this.competitionEndDate, team.getTeamName(), team.getLeader().getUsername(),
				this.teamRed.equals(team) ? CompetitionTeamColor.RED : CompetitionTeamColor.BLUE);
	}
	
	public CompetitionHistory toCompetitionHistory(Team team) {
		if (!(this.teamRed.equals(team) || this.teamBlue.equals(team)))
			throw new IllegalArgumentException("Invalid team.");
		int redBlueDiff = this.teamRedScore - this.teamBlueScore;
		CompetitionResult competitionResult = redBlueDiff == 0 ? CompetitionResult.TIED :
					this.teamRed.equals(team) ? (redBlueDiff > 0 ? CompetitionResult.WON : CompetitionResult.LOST) :
												(redBlueDiff > 0 ? CompetitionResult.LOST : CompetitionResult.WON);
					
		return new CompetitionHistory(this.competitionName, this.competitionId, competitionResult,
				this.teamRed.getTeamName(), this.teamBlue.getTeamName(), this.teamRedScore, this.teamBlueScore,
				this.teamRedLeft, this.teamBlueLeft, this.competitionStartDate, this.competitionEndDate);
	}
}
