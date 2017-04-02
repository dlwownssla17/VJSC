package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import util.CreateLookupDate;

public class Competition {
	private String competitionName;
	private long competitionId;
	
	private Date competitionStartDate, competitionEndDate;
	
	private long teamRedId, teamBlueId;
	private String teamRedName, teamBlueName;
	private String teamRedLeaderUsername, teamBlueLeaderUsername;
	private HashMap<String, Integer> teamRedScores, teamBlueScores;
	private boolean teamRedLeft, teamBlueLeft;
	
	private boolean showTeamMembers;
	private boolean status; // true - active, false - pending
	
	public Competition(String competitionName, long competitionId, Date competitionStartDate, Date competitionEndDate,
			long teamRedId, long teamBlueId, String teamRedName, String teamBlueName,
			String teamRedLeaderUsername, String teamBlueLeaderUsername, boolean showTeamMembers) {
		this.competitionName = competitionName;
		this.competitionId = competitionId;
		
		if (CreateLookupDate.getInstance(new Date()).after(competitionStartDate) ||
				competitionStartDate.after(competitionEndDate))
			throw new IllegalArgumentException("Invalid start/end dates.");
		this.competitionStartDate = competitionStartDate;
		this.competitionEndDate = competitionEndDate;
		
		if (teamRedId == teamBlueId) throw new IllegalArgumentException("Teams must be different.");
		this.teamRedId = teamRedId;
		this.teamBlueId = teamBlueId;
		this.teamRedName = teamRedName;
		this.teamBlueName = teamBlueName;
		this.teamRedLeaderUsername = teamRedLeaderUsername;
		this.teamBlueLeaderUsername = teamBlueLeaderUsername;
		this.teamRedScores = new HashMap<>();
		this.teamBlueScores = new HashMap<>();
		this.teamRedLeft = false;
		this.teamBlueLeft = false;
		
		this.showTeamMembers = showTeamMembers;
		this.status = false;
	}
	
	@Override
	public boolean equals(Object otherCompetition) {
		return otherCompetition instanceof Competition &&
				this.competitionId == ((Competition) otherCompetition).getCompetitionId();
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
	
	public long getTeamId(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedId;
			case BLUE:
				return this.teamBlueId;
			default:
				return -1;
		}
	}
	
	public long setTeamId(CompetitionTeamColor color, long teamId) {
		switch(color) {
			case RED:
				this.teamRedId = teamId;
				return this.teamRedId;
			case BLUE:
				this.teamBlueId = teamId;
				return this.teamBlueId;
			default:
				return -1;
		}
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
	
	public String getTeamLeaderUsername(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedLeaderUsername;
			case BLUE:
				return this.teamBlueLeaderUsername;
			default:
				return null;
		}
	}
	
	public String setTeamLeaderUsername(CompetitionTeamColor color, String teamLeaderUsername) {
		switch(color) {
			case RED:
				this.teamRedLeaderUsername = teamLeaderUsername;
				return this.teamRedLeaderUsername;
			case BLUE:
				this.teamBlueLeaderUsername = teamLeaderUsername;
				return this.teamBlueLeaderUsername;
			default:
				return null;
		}
	}
	
	synchronized public HashMap<String, Integer> getTeamScores(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedScores;
			case BLUE:
				return this.teamBlueScores;
			default:
				return null;
		}
	}
	
	synchronized public HashMap<String, Integer> setTeamScores(CompetitionTeamColor color,
																		HashMap<String, Integer> teamScores) {
		switch(color) {
			case RED:
				this.teamRedScores = teamScores;
				return this.teamRedScores;
			case BLUE:
				this.teamBlueScores = teamScores;
				return this.teamBlueScores;
			default:
				return null;
		}
	}
	
	synchronized public Set<String> getTeamMembers(CompetitionTeamColor color) {
		switch(color) {
			case RED:
				return this.teamRedScores.keySet();
			case BLUE:
				return this.teamBlueScores.keySet();
			default:
				return null;
		}
	}
	
	synchronized public boolean hasTeamMemberScore(CompetitionTeamColor color, String memberUsername) {
		switch(color) {
			case RED:
				return this.teamRedScores.containsKey(memberUsername);
			case BLUE:
				return this.teamBlueScores.containsKey(memberUsername);
			default:
				return false;
		}
	}
	
	synchronized public int createTeamMemberScore(CompetitionTeamColor color, String memberUsername) {
		HashMap<String, Integer> teamScores = null;
		switch(color) {
			case RED:
				teamScores = this.teamRedScores;
				break;
			case BLUE:
				teamScores = this.teamBlueScores;
				break;
			default:
				return -1;
		}
		if (teamScores.get(memberUsername) == null) teamScores.put(memberUsername, 0);
		return teamScores.get(memberUsername);
	}
	
	synchronized public int getTeamMemberScore(CompetitionTeamColor color, String memberUsername) {
		HashMap<String, Integer> teamScores = null;
		switch(color) {
			case RED:
				teamScores = this.teamRedScores;
				break;
			case BLUE:
				teamScores = this.teamBlueScores;
				break;
			default:
				return -1;
		}
		return teamScores.get(memberUsername) == null ? teamScores.get(memberUsername) : -1;
	}
	
	synchronized public int setTeamMemberScore(CompetitionTeamColor color, String memberUsername, int score) {
		HashMap<String, Integer> teamScores = null;
		switch(color) {
			case RED:
				teamScores = this.teamRedScores;
				break;
			case BLUE:
				teamScores = this.teamBlueScores;
				break;
			default:
				return -1;
		}
		teamScores.put(memberUsername, score);
		return score;
	}
	
	synchronized public int addTeamMemberScore(CompetitionTeamColor color, String memberUsername, int scoreAdded) {
		HashMap<String, Integer> teamScores = null;
		switch(color) {
			case RED:
				teamScores = this.teamRedScores;
				break;
			case BLUE:
				teamScores = this.teamBlueScores;
				break;
			default:
				return -1;
		}
		int newScore = teamScores.get(memberUsername) + scoreAdded;
		teamScores.put(memberUsername, newScore);
		return newScore;
	}
	
	synchronized public int removeTeamMemberScore(CompetitionTeamColor color, String memberUsername) {
		switch(color) {
			case RED:
				return this.teamRedScores.remove(memberUsername);
			case BLUE:
				return this.teamBlueScores.remove(memberUsername);
			default:
				return -1;
		}
	}
	
	synchronized public int getTeamTotalScore(CompetitionTeamColor color) {
		HashMap<String, Integer> teamScores = null;
		switch(color) {
			case RED:
				teamScores = this.teamRedScores;
				break;
			case BLUE:
				teamScores = this.teamBlueScores;
				break;
			default:
				return -1;
		}
		int sum = 0;
		for (String memberUsername : teamScores.keySet()) {
			sum += teamScores.get(memberUsername);
		}
		return sum;
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
	
	public CompetitionInvitation toCompetitionInvitation(long teamId) {
		if (!(this.teamRedId == teamId || this.teamBlueId == teamId))
			throw new IllegalArgumentException("Invalid team.");
		return new CompetitionInvitation(this.competitionName, this.competitionId,
				this.competitionStartDate, this.competitionEndDate,
				this.teamRedId == teamId ? this.teamRedName : this.teamBlueName,
				this.teamRedId == teamId ? this.teamRedLeaderUsername : this.teamBlueLeaderUsername,
				this.teamRedId == teamId ? CompetitionTeamColor.RED : CompetitionTeamColor.BLUE);
	}
	
	public CompetitionHistory toCompetitionHistory(long teamId) {
		if (!(this.teamRedId == teamId || this.teamBlueId == teamId))
			throw new IllegalArgumentException("Invalid team.");
		int teamRedTotalScore = this.getTeamTotalScore(CompetitionTeamColor.RED);
		int teamBlueTotalScore = this.getTeamTotalScore(CompetitionTeamColor.BLUE);
		int redBlueDiff = teamRedTotalScore - teamBlueTotalScore;
		CompetitionResult competitionResult = redBlueDiff == 0 ? CompetitionResult.TIED :
					this.teamRedId == teamId ? (redBlueDiff > 0 ? CompetitionResult.WON : CompetitionResult.LOST) :
												(redBlueDiff > 0 ? CompetitionResult.LOST : CompetitionResult.WON);
					
		return new CompetitionHistory(this.competitionName, this.competitionId, competitionResult,
				this.teamRedName, this.teamBlueName, teamRedTotalScore, teamBlueTotalScore,
				this.teamRedLeft, this.teamBlueLeft, this.competitionStartDate, this.competitionEndDate);
	}
}
