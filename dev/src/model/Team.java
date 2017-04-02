package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Team {
	private long teamId;
	private String teamName;
	private Date teamCreated;
	
	private int maxTeamSize;
	
	private String leaderUsername;
	private ArrayList<String> memberUsernames;
	private HashMap<String, Date> inTeamSince;
	
	private HashSet<String> usersInvited;
	
	private long competitionId;
	private ArrayList<CompetitionInvitation> competitionInvitations;
	private ArrayList<CompetitionHistory> competitionHistories;
	
	public Team(long teamId, String teamName, String leaderUsername, int maxTeamSize) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamCreated = new Date();
		
		this.maxTeamSize = maxTeamSize;
		
		this.leaderUsername = leaderUsername;
		this.memberUsernames = new ArrayList<>();
		this.inTeamSince = new HashMap<>();
		
		this.usersInvited = new HashSet<>();
		
		this.competitionId = -1;
		this.competitionInvitations = new ArrayList<>();
		this.competitionHistories = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object otherTeam) {
		return otherTeam instanceof Team && this.teamId == ((Team) otherTeam).getTeamId()
				&& this.teamName.equals(((Team) otherTeam).getTeamName());
	}
	
	public long getTeamId() {
		return this.teamId;
	}
	
	public long setTeamId(long teamId) {
		this.teamId = teamId;
		return this.teamId;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String setTeamName(String teamName) {
		this.teamName = teamName;
		return this.teamName;
	}
	
	public Date getTeamCreated() {
		return this.teamCreated;
	}
	
	public Date setTeamCreated(Date teamCreated) {
		this.teamCreated = teamCreated;
		return this.teamCreated;
	}
	
	public int getMaxTeamSize() {
		return this.maxTeamSize;
	}
	
	public int setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
		return this.maxTeamSize;
	}
	
	public String getLeaderUsername() {
		return this.leaderUsername;
	}
	
	public String setLeaderUsername(String leaderUsername) {
		this.leaderUsername = leaderUsername;
		return this.leaderUsername;
	}
	
	public ArrayList<String> getMemberUsernames() {
		return this.memberUsernames;
	}
	
	public ArrayList<String> setMemberUsernames(ArrayList<String> memberUsernames) {
		this.memberUsernames = memberUsernames;
		return this.memberUsernames;
	}
	
	synchronized boolean addMemberUsername(String username) {
		if (this.maxTeamSize > 0 && this.memberUsernames.size() >= this.maxTeamSize) return false;
		
		this.memberUsernames.add(username);
		this.inTeamSince.put(username, new Date());
		return true;
	}
	
	synchronized boolean removeMemberUsername(String username) {
		String memberUsernameToRemove = null;
		for (String memberUsername : this.memberUsernames) {
			if (memberUsername.equals(username)) {
				memberUsernameToRemove = memberUsername;
				break;
			}
		}
		return memberUsernameToRemove != null ? this.memberUsernames.remove(memberUsernameToRemove) &&
										this.inTeamSince.remove(memberUsernameToRemove) != null : false;
	}
	
	public int getTeamSize() {
		return this.memberUsernames.size();
	}
	
	public HashMap<String, Date> getInTeamSince() {
		return this.inTeamSince;
	}
	
	public HashMap<String, Date> setInTeamSince(HashMap<String, Date> inTeamSince) {
		this.inTeamSince = inTeamSince;
		return this.inTeamSince;
	}
	
	public Date getInTeamSinceForMemberUsername(String username) {
		return this.inTeamSince.get(username);
	}
	
	public HashSet<String> getUsersInvited() {
		return this.usersInvited;
	}
	
	public HashSet<String> setUsersInvited(HashSet<String> usersInvited) {
		this.usersInvited = usersInvited;
		return this.usersInvited;
	}
	
	public boolean isUserInvited(String usernameInvited) {
		return this.usersInvited.contains(usernameInvited);
	}
	
	public boolean addUserInvited(String usernameInvited) {
		return this.usersInvited.add(usernameInvited);
	}
	
	public boolean removeUserInvited(String usernameInvited) {
		return this.usersInvited.remove(usernameInvited);
	}
	
	public long getCompetitionId() {
		return this.competitionId;
	}
	
	public long setCompetitionId(long competitionId) {
		this.competitionId = competitionId;
		return this.competitionId;
	}
	
	public boolean hasCompetition() {
		return this.competitionId >= 0;
	}
	
	public ArrayList<CompetitionInvitation> getCompetitionInvitations() {
		return this.competitionInvitations;
	}
	
	public ArrayList<CompetitionInvitation> setCompetitionInvitations
													(ArrayList<CompetitionInvitation> competitionInvitations) {
		this.competitionInvitations = competitionInvitations;
		return this.competitionInvitations;
	}
	
	public boolean addCompetitionInvitation(CompetitionInvitation competitionInvitation) {
		return this.competitionInvitations.add(competitionInvitation);
	}
	
	public boolean declineCompetitionInvitation(long competitionId) {
		CompetitionInvitation competitionInvitationToRemove = null;
		for (CompetitionInvitation competitionInvitation : this.competitionInvitations) {
			if (competitionInvitation.getCompetitionId() == competitionId) {
				competitionInvitationToRemove = competitionInvitation;
				break;
			}
		}
		return competitionInvitationToRemove != null ?
				this.competitionInvitations.remove(competitionInvitationToRemove) : false;
	}
	
	public ArrayList<CompetitionHistory> getCompetitionHistories() {
		return this.competitionHistories;
	}
	
	public ArrayList<CompetitionHistory> setCompetitionHistories(ArrayList<CompetitionHistory> competitionHistories) {
		this.competitionHistories = competitionHistories;
		return this.competitionHistories;
	}
	
	public boolean addCompetitionHistory(CompetitionHistory competitionHistory) {
		return this.competitionHistories.add(competitionHistory);
	}
	
	public TeamInvitation toTeamInvitation() {
		return new TeamInvitation(this.teamName, this.teamId, this.leaderUsername, this.teamCreated);
	}
}
