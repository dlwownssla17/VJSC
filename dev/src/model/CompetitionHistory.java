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
	
	public String getTeamName(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedName;
			case BLUE:
				return this.teamBlueName;
			default:
				return null;
		}
	}
	
	public String setTeamName(CompetitionTeamColor color, String teamName) {
		switch(color) {
			case RED:
				this.teamRedName = teamName;
				return this.teamRedName;
			case BLUE:
				this.teamBlueName = teamName;
				return this.teamBlueName;
			default:
				return null;
		}
	}
	
	public int getTeamScore(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedScore;
			case BLUE:
				return this.teamBlueScore;
			default:
				return -1;
		}
	}
	
	public int setTeamScore(CompetitionTeamColor color, int teamScore) {
		switch(color) {
			case RED:
				this.teamRedScore = teamScore;
				return this.teamRedScore;
			case BLUE:
				this.teamBlueScore = teamScore;
				return this.teamBlueScore;
			default:
				return -1;
		}
	}
	
	public boolean getTeamLeft(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedLeft;
			case BLUE:
				return this.teamBlueLeft;
			default:
				return false;
		}
	}
	
	public boolean setTeamLeft(CompetitionTeamColor color, boolean teamLeft) {
		switch(color) {
			case RED:
				this.teamRedLeft = teamLeft;
				return this.teamRedLeft;
			case BLUE:
				this.teamBlueLeft = teamLeft;
				return this.teamBlueLeft;
			default:
				return false;
		}
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
