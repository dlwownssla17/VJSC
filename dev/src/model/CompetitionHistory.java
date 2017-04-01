package model;

import java.util.Date;

public class CompetitionHistory {
	private String competitionName;
	private long competitionId;
	private CompetitionResult competitionResult;
	
	private String teamRedName, teamBlueName;
	private int teamRedScore, teamBlueScore;
	private boolean teamRedLeft, teamBlueLeft;
	
	private Date competitionStartDate, competitionEndDate;
	
	public CompetitionHistory(String competitionName, long competitionId, CompetitionResult competitionResult,
			String teamRedName, String teamBlueName, int teamRedScore, int teamBlueScore,
			boolean teamRedLeft, boolean teamBlueLeft, Date competitionStartDate, Date competitionEndDate) {
		this.competitionName = competitionName;
		this.competitionId = competitionId;
		this.competitionResult = competitionResult;
		
		this.teamRedName = teamRedName;
		this.teamBlueName = teamBlueName;
		this.teamRedScore = teamRedScore;
		this.teamBlueScore = teamBlueScore;
		this.teamRedLeft = teamRedLeft;
		this.teamBlueLeft = teamBlueLeft;
		
		this.competitionStartDate = competitionStartDate;
		this.competitionEndDate = competitionEndDate;
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
	
	public CompetitionResult getCompetitionResult() {
		return this.competitionResult;
	}
	
	public CompetitionResult setCompetitionResult(CompetitionResult competitionResult) {
		this.competitionResult = competitionResult;
		return this.competitionResult;
	}
	
	public String getTeamRedName() {
		return this.teamRedName;
	}
	
	public String setTeamRedName(String teamRedName) {
		this.teamRedName = teamRedName;
		return this.teamRedName;
	}
	
	public int getTeamRedScore() {
		return this.teamRedScore;
	}
	
	public int setTeamRedScore(int teamRedScore) {
		this.teamRedScore = teamRedScore;
		return this.teamRedScore;
	}
	
	public boolean getTeamRedLeft() {
		return this.teamRedLeft;
	}
	
	public boolean setTeamRedLeft(boolean teamRedLeft) {
		this.teamRedLeft = teamRedLeft;
		return this.teamRedLeft;
	}
	
	public String getTeamBlueName() {
		return this.teamBlueName;
	}
	
	public String setTeamBlueName(String teamBlueName) {
		this.teamBlueName = teamBlueName;
		return this.teamBlueName;
	}
	
	public int getTeamBlueScore() {
		return this.teamBlueScore;
	}
	
	public int setTeamBlueScore(int teamBlueScore) {
		this.teamBlueScore = teamBlueScore;
		return this.teamBlueScore;
	}
	
	public boolean getTeamBlueLeft() {
		return this.teamBlueLeft;
	}
	
	public boolean setTeamBlueLeft(boolean teamBlueLeft) {
		this.teamBlueLeft = teamBlueLeft;
		return this.teamBlueLeft;
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
}
