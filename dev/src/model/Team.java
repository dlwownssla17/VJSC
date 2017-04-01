package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Team {
	private long teamId;
	private String teamName;
	private Date teamCreated;
	
	private int maxTeamSize;
	
	private User leader;
	private ArrayList<User> members;
	private HashMap<User, Date> inTeamSince;
	
	private Competition competition;
	private ArrayList<CompetitionInvitation> competitionInvitations;
	private ArrayList<CompetitionHistory> competitionHistories;
	
	public Team(long teamId, String teamName, User leader, int maxTeamSize) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamCreated = new Date();
		
		this.maxTeamSize = maxTeamSize;
		
		this.leader = leader;
		this.members = new ArrayList<>();
		this.inTeamSince = new HashMap<>();
		
		this.competitionInvitations = new ArrayList<>();
		this.competitionHistories = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object otherTeam) {
		return otherTeam instanceof Team && this.teamId == ((Team) otherTeam).getTeamId();
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
	
	public User getLeader() {
		return this.leader;
	}
	
	public User setLeader(User user) {
		this.leader = user;
		return this.leader;
	}
	
	public ArrayList<User> getMembers() {
		return this.members;
	}
	
	public ArrayList<User> setMembers(ArrayList<User> members) {
		this.members = members;
		return this.members;
	}
	
	synchronized boolean addMember(User user) {
		if (this.maxTeamSize > 0 && this.members.size() == this.maxTeamSize) return false;
		
		this.members.add(user);
		this.inTeamSince.put(user, new Date());
		return true;
	}
	
	synchronized boolean removeMember(User user) {
		User memberToRemove = null;
		for (User member : this.members) {
			if (member.getUsername().equals(user.getUsername())) {
				memberToRemove = member;
				break;
			}
		}
		return memberToRemove != null ? this.members.remove(memberToRemove) &&
										this.inTeamSince.remove(memberToRemove) != null : false;
	}
	
	public int getTeamSize() {
		return this.members.size();
	}
	
	public HashMap<User, Date> getInTeamSince() {
		return this.inTeamSince;
	}
	
	public HashMap<User, Date> setInTeamSince(HashMap<User, Date> inTeamSince) {
		this.inTeamSince = inTeamSince;
		return this.inTeamSince;
	}
	
	public Date getInTeamSinceForMember(User user) {
		return this.inTeamSince.get(user);
	}
	
	public Competition getCompetition() {
		return this.competition;
	}
	
	public Competition setCompetition(Competition competition) {
		this.competition = competition;
		return this.competition;
	}
	
	public boolean hasCompetition() {
		return this.competition != null;
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
		return new TeamInvitation(this.teamName, this.teamId, this.leader.getUsername(), this.teamCreated);
	}
}
