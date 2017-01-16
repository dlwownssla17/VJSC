package model;

import fitbit.FitBitAccount;

public class User {
	private String username;
	private String password;
	private UserInfo info;
	private UserSettings settings;
	private UserDiabetesParams diabetesParams;
	private UserAdherenceParams adherenceParams;
	private UserCommunityParams communityParams;
	private UserSchedule schedule;
	private BehaviorTPM tpm;
	
	private FitBitAccount fitBitAccount;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		// TODO username and password validation
		// TODO decide on initialization of all the other fields
	}
	
	// TODO decide on how to initialize from initial survey upon registration
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String setPassword(String password) {
		this.password = password;
		// TODO same or different password validation
		return getPassword();
	}
	
	public boolean linkFitBitAccount(FitBitAccount fitBitAccount) {
		this.fitBitAccount = fitBitAccount;
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}
