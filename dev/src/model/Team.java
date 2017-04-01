package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Team {
	private long teamId;
	private String teamName;
	private Date teamCreated;
	
	private User leader;
	private ArrayList<User> members;
	private HashMap<User, Date> inTeamSince;
	
	private int maxTeamSize;
	
	public Team(long teamId, String teamName, User leader, int maxTeamSize) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamCreated = new Date();
		
		this.leader = leader;
		this.members = new ArrayList<>();
		this.inTeamSince = new HashMap<>();
		
		this.maxTeamSize = maxTeamSize;
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
	
	public int getTeamSize() {
		return this.members.size();
	}
	
	public int getMaxTeamSize() {
		return this.maxTeamSize;
	}
	
	public int setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
		return this.maxTeamSize;
	}
}
