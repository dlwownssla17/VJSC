package model;

import java.util.Date;

public class TeamInvitation {
	private String teamName;
	private long teamId;
	private String teamLeaderUsername;
	private Date teamCreated;
	
	public TeamInvitation(String teamName, long teamId, String teamLeaderUsername, Date teamCreated) {
		this.teamName = teamName;
		this.teamId = teamId;
		this.teamLeaderUsername = teamLeaderUsername;
		this.teamCreated = teamCreated;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String setTeamName(String teamName) {
		this.teamName = teamName;
		return this.teamName;
	}
	
	public long getTeamId() {
		return this.teamId;
	}
	
	public long setTeamId(long teamId) {
		this.teamId = teamId;
		return this.teamId;
	}
	
	public String getTeamLeaderUsername() {
		return this.teamLeaderUsername;
	}
	
	public String setTeamLeaderUsername(String teamLeaderUsername) {
		this.teamLeaderUsername = teamLeaderUsername;
		return this.teamLeaderUsername;
	}
	
	public Date getTeamCreated() {
		return this.teamCreated;
	}
	
	public Date setTeamCreated(Date teamCreated) {
		this.teamCreated = teamCreated;
		return this.teamCreated;
	}
}
