package model;

import java.util.ArrayList;

public class Team {
	private String teamName;
	private ArrayList<User> users;
	
	public Team(String teamName) {
		this.teamName = teamName;
		this.users = new ArrayList<>();
	}
	
	public boolean addUser(User user) {
		return this.users.add(user);
	}
	
	public boolean removeUser(User user) {
		return this.users.remove(user);
	}
}
