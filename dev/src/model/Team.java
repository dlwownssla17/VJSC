package model;

import java.util.ArrayList;

public class Team {
	private String teamName;
	private ArrayList<User> users;
	
	public Team(String teamName) {
		this.teamName = teamName;
		this.users = new ArrayList<>();
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String setTeamName(String teamName) {
		this.teamName = teamName;
		return getTeamName();
	}
	
	public boolean addUser(User user) {
		return this.users.add(user);
	}
	
	public boolean removeUser(User user) {
		return this.users.remove(user);
	}
	
	public ArrayList<User> getUsers() {
		return this.users;
	}
	
	public int getTeamSize() {
		return this.users.size();
	}
}
