package model;

import java.util.Date;

public class CompetitionInvitation {
	private String competitionName;
	private long competitionId;
	private Date competitionStartDate, competitionEndDate;
	
	private String otherTeamName;
	private String otherTeamLeaderUsername;
	private CompetitionTeamColor otherTeamColor;
	
	public CompetitionInvitation(String competitionName, long competitionId,
			Date competitionStartDate, Date competitionEndDate, String otherTeamName,
			String otherTeamLeaderUsername, CompetitionTeamColor otherTeamColor) {
		this.competitionName = competitionName;
		this.competitionId = competitionId;
		this.competitionStartDate = competitionStartDate;
		this.competitionEndDate = competitionEndDate;
		
		this.otherTeamName = otherTeamName;
		this.otherTeamLeaderUsername = otherTeamLeaderUsername;
		this.otherTeamColor = otherTeamColor;
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
	
	public String getOtherTeamName() {
		return this.otherTeamName;
	}
	
	public String setOtherTeamName(String otherTeamName) {
		this.otherTeamName = otherTeamName;
		return this.otherTeamName;
	}
	
	public String getOtherTeamLeaderUsername() {
		return this.otherTeamLeaderUsername;
	}
	
	public String setOtherTeamLeaderUsername(String otherTeamLeaderUsername) {
		this.otherTeamLeaderUsername = otherTeamLeaderUsername;
		return this.otherTeamLeaderUsername;
	}
	
	public CompetitionTeamColor getOtherTeamColor() {
		return this.otherTeamColor;
	}
	
	public CompetitionTeamColor setOtherTeamColor(CompetitionTeamColor otherTeamColor) {
		this.otherTeamColor = otherTeamColor;
		return this.otherTeamColor;
	}
}
